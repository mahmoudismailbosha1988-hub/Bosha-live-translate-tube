package com.arabicvideotranslator

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private var subtitleView: TextView? = null

    override fun onCreate() {
        super.onCreate()

        windowManager =
            getSystemService(WINDOW_SERVICE)
                    as WindowManager

        subtitleView = TextView(this).apply {

            textSize = 22f

            setTextColor(
                android.graphics.Color.WHITE
            )

            setBackgroundColor(
                0x66000000
            )

            text = "الترجمة ستظهر هنا"
        }

        val params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

        params.gravity = Gravity.BOTTOM

        windowManager.addView(
            subtitleView,
            params
        )
    }

    override fun onDestroy() {

        subtitleView?.let {
            windowManager.removeView(it)
        }

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
