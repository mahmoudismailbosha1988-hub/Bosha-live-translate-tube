package com.arabicvideotranslator.audio

import android.media.AudioRecord
import com.arabicvideotranslator.AppConstants

class AudioCaptureManager {

    private var audioRecord: AudioRecord? = null

    fun initializeAudioRecord(audioRecord: AudioRecord) {
        this.audioRecord = audioRecord
    }

    fun captureAudio(): ShortArray {
        val audioRecord = this.audioRecord ?: return ShortArray(0)

        val buffer = ShortArray(AppConstants.AUDIO_BUFFER_SIZE)
        val readSize = audioRecord.read(buffer, 0, buffer.size)

        return if (readSize > 0) {
            buffer.sliceArray(0 until readSize)
        } else {
            ShortArray(0)
        }
    }

    fun releaseAudioRecord() {
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
}