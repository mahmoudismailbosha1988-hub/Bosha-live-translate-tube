package com.arabicvideotranslator.audio

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AudioCaptureService : Service() {

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        // سيتم إضافة التقاط صوت الفيديو هنا لاحقًا

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        // سيتم إيقاف التقاط الصوت هنا لاحقًا
        super.onDestroy()
    }
}
