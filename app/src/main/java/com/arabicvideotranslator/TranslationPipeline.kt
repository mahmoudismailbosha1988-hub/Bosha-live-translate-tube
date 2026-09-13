package com.arabicvideotranslator

import android.content.Context
import com.arabicvideotranslator.audio.AudioCaptureManager
import com.arabicvideotranslator.speech.SpeechRecognitionEngine
import com.arabicvideotranslator.speech.SpeechRecognitionListener
import com.arabicvideotranslator.translation.TranslationEngine
import com.arabicvideotranslator.translation.TranslationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface PipelineListener {
    fun onTranslationReady(translatedText: String)
    fun onError(error: String)
}

class TranslationPipeline(
    private val context: Context,
    private val audioCaptureManager: AudioCaptureManager
) : SpeechRecognitionListener, TranslationListener {

    private val speechEngine = SpeechRecognitionEngine(context)
    private val translationEngine = TranslationEngine()
    private var pipelineListener: PipelineListener? = null
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private var isRunning = false

    init {
        speechEngine.setListener(this)
        translationEngine.setListener(this)
    }

    fun setListener(listener: PipelineListener) {
        this.pipelineListener = listener
    }

    fun startProcessing() {
        if (isRunning) return
        isRunning = true

        scope.launch {
            while (isRunning) {
                try {
                    // 1. التقاط الصوت
                    val audioBuffer = audioCaptureManager.captureAudio()

                    if (audioBuffer.isNotEmpty()) {
                        // 2. التعرف على الكلام
                        val recognizedText = speechEngine.recognizeAudio(audioBuffer)

                        if (recognizedText.isNotEmpty()) {
                            // 3. ترجمة النص
                            val translatedText = translationEngine.translate(recognizedText)

                            if (translatedText.isNotEmpty()) {
                                pipelineListener?.onTranslationReady(translatedText)
                            }
                        }
                    }

                    // تأخير صغير لتجنب استهلاك CPU
                    Thread.sleep(100)
                } catch (e: Exception) {
                    pipelineListener?.onError("خطأ في المعالجة: ${e.message}")
                }
            }
        }
    }

    fun stopProcessing() {
        isRunning = false
        scope.launch {
            speechEngine.release()
            translationEngine.release()
            audioCaptureManager.releaseAudioRecord()
        }
    }

    override fun onRecognitionResult(text: String) {
        scope.launch {
            val translatedText = translationEngine.translate(text)
            if (translatedText.isNotEmpty()) {
                pipelineListener?.onTranslationReady(translatedText)
            }
        }
    }

    override fun onRecognitionError(error: String) {
        pipelineListener?.onError(error)
    }

    override fun onTranslationResult(translatedText: String) {
        pipelineListener?.onTranslationReady(translatedText)
    }

    override fun onTranslationError(error: String) {
        pipelineListener?.onError(error)
    }

    fun release() {
        stopProcessing()
        pipelineListener = null
    }
}