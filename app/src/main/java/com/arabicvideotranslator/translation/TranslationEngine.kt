package com.arabicvideotranslator.translation

interface TranslationEngine {

    suspend fun translate(
        text: String,
        sourceLanguage: String = "en",
        targetLanguage: String = "ar"
    ): String
}
