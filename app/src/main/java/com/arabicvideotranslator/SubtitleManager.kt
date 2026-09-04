package com.arabicvideotranslator

class SubtitleManager {

    private var currentSubtitle: String = ""

    fun setSubtitle(text: String) {
        currentSubtitle = text
    }

    fun getSubtitle(): String {
        return currentSubtitle
    }

    fun clearSubtitle() {
        currentSubtitle = ""
    }
}
