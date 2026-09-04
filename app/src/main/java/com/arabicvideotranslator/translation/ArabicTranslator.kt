package com.arabicvideotranslator.translation

class ArabicTranslator : TranslationEngine {

    override suspend fun translate(
        text: String,
        sourceLanguage: String,
        targetLanguage: String
    ): String {
        if (text.isBlank()) {
            return ""
        }

        // سيتم ربط محرك الترجمة الفعلي هنا لاحقًا
        return text
    }

    override fun isAvailable(): Boolean {
        return true
    }
}
