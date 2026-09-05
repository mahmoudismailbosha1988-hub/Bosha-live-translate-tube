package com.arabicvideotranslator

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {

    private lateinit var wm: WindowManager
    private lateinit var subtitleView: TextView

    override fun onCreate() {
        super.onCreate()

        wm = getSystemService(WINDOW_SERVICE) as WindowManager

        subtitleView = TextView(this).apply {
            text = "Bosha Live Translate"
            textSize = 20f
            setTextColor(Color.WHITE)
            setBackgroundColor(0x88000000.toInt())
            gravity = Gravity.CENTER
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.BOTTOM

        wm.addView(subtitleView, params)
    }

    fun updateSubtitle(text: String) {
        subtitleView.text = text
    }

    override fun onDestroy() {
        wm.removeView(subtitleView)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
