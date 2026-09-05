package com.arabicvideotranslator.translation

class ArabicTranslator : TranslationEngine {

    private val dictionary = mapOf(
        "hello" to "مرحبا",
        "welcome" to "أهلا بك",
        "good morning" to "صباح الخير",
        "good evening" to "مساء الخير",
        "thank you" to "شكرا لك",
        "yes" to "نعم",
        "no" to "لا"
    )

    override suspend fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String {

        if (text.isBlank()) {
            return ""
        }

        val result =
            dictionary[text.lowercase()]

        return result ?: text
    }

    override fun isAvailable(): Boolean {
        return true
    }
}
