package com.arabicvideotranslator.audio

class AudioCaptureManager {

    private var isCapturing = false

    fun start() {
        isCapturing = true
        // سيتم ربط التقاط صوت الفيديو هنا لاحقًا
    }

    fun stop() {
        isCapturing = false
        // سيتم إيقاف التقاط الصوت هنا لاحقًا
    }

    fun isRunning(): Boolean {
        return isCapturing
    }
}
