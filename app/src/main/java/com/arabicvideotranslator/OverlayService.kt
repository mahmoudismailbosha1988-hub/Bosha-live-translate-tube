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
    private var currentText: String = ""

    override fun onCreate() {
        super.onCreate()

        wm = getSystemService(WINDOW_SERVICE) as WindowManager

        subtitleView = TextView(this).apply {
            text = AppConstants.OVERLAY_TEXT
            textSize = 16f
            setTextColor(Color.WHITE)
            setBackgroundColor(0xAA000000.toInt())
            gravity = Gravity.CENTER
            setPadding(16, 8, 16, 8)
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.BOTTOM
        params.y = 100

        wm.addView(subtitleView, params)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val translation = intent?.getStringExtra("translation") ?: ""
        if (translation.isNotEmpty()) {
            updateSubtitle(translation)
        }
        return START_STICKY
    }

    fun updateSubtitle(text: String) {
        currentText = text
        subtitleView.text = text
    }

    override fun onDestroy() {
        try {
            wm.removeView(subtitleView)
        } catch (e: Exception) {
            // View might not be attached
        }
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}