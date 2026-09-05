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

        return "hello"
    }

    fun release() {
        initialized = false
    }

    fun isReady(): Boolean {
        return initialized
    }
}
