package com.arabicvideotranslator.speech

class WhisperEngine {

    private var initialized = false

    fun initialize() {
        initialized = true
    }

    fun transcribe(audioData: ShortArray): String {

        if (!initialized) {
            return ""
        }

        if (audioData.isEmpty()) {
            return ""
        }

        val averageLevel =
            audioData.map { kotlin.math.abs(it.toInt()) }
                .average()

        return if (averageLevel > 500) {
            "Speech detected"
        } else {
            ""
        }
    }

    fun release() {
        initialized = false
    }

    fun isReady(): Boolean {
        return initialized
    }
}
