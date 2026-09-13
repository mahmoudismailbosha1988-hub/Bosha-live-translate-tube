package com.arabicvideotranslator.translation

class ArabicTranslator {

    private val dictionary = mapOf(
        "hello" to "مرحبا",
        "welcome" to "أهلا بك",
        "good morning" to "صباح الخير",
        "good evening" to "مساء الخير",
        "thank you" to "شكرا لك",
        "yes" to "نعم",
        "no" to "لا"
    )

    suspend fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String {

        if (text.isBlank()) {
            return ""
        }

        return dictionary[text.lowercase()] ?: text
    }

    fun isAvailable(): Boolean {
        return true
    }
}
