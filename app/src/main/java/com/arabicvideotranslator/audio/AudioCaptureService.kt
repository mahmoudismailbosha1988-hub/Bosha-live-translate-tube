package com.arabicvideotranslator.audio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioPlaybackCaptureConfiguration
import android.media.AudioRecord
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder

class AudioCaptureService : Service() {

    companion object {
        const val EXTRA_RESULT_CODE = "result_code"
        const val EXTRA_RESULT_DATA = "result_data"

        private const val CHANNEL_ID = "audio_capture_channel"
        private const val NOTIFICATION_ID = 1001
    }

    private var mediaProjection: MediaProjection? = null
    private var audioRecord: AudioRecord? = null
    private var captureThread: Thread? = null
    private var isCapturing = false

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        createNotificationChannel()
        startForeground(
            NOTIFICATION_ID,
            createNotification()
        )

        val resultCode = intent?.getIntExtra(
            EXTRA_RESULT_CODE,
            -1
        ) ?: -1

        val resultData = intent?.getParcelableExtra<Intent>(
            EXTRA_RESULT_DATA
        )

        if (resultCode != -1 && resultData != null) {
            startAudioCapture(
                resultCode,
                resultData
            )
        }

        return START_NOT_STICKY
    }

    private fun startAudioCapture(
        resultCode: Int,
        resultData: Intent
    ) {
        if (isCapturing) {
            return
        }

        val projectionManager =
            getSystemService(
                Context.MEDIA_PROJECTION_SERVICE
            ) as MediaProjectionManager

        mediaProjection =
            projectionManager.getMediaProjection(
                resultCode,
                resultData
            )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return
        }

        val playbackConfig =
            AudioPlaybackCaptureConfiguration.Builder(
                mediaProjection!!
            )
                .addMatchingUsage(
                    AudioAttributes.USAGE_MEDIA
                )
                .build()

        val audioFormat =
            AudioFormat.Builder()
                .setEncoding(
                    AudioFormat.ENCODING_PCM_16BIT
                )
                .setSampleRate(16000)
                .setChannelMask(
                    AudioFormat.CHANNEL_IN_MONO
                )
                .build()

        val bufferSize =
            AudioRecord.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

        audioRecord =
            AudioRecord.Builder()
                .setAudioFormat(audioFormat)
                .setBufferSizeInBytes(
                    bufferSize * 2
                )
                .setAudioPlaybackCaptureConfig(
                    playbackConfig
                )
                .build()

        audioRecord?.startRecording()
        isCapturing = true

        captureThread = Thread {
            val buffer = ShortArray(1600)

            while (isCapturing) {
                val read =
                    audioRecord?.read(
                        buffer,
                        0,
                        buffer.size
                    ) ?: 0

                if (read > 0) {
                    // سيتم إرسال الصوت إلى Whisper في الخطوة التالية
                }
            }
        }

        captureThread?.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Bosha Live Translate Tube",
                    NotificationManager.IMPORTANCE_LOW
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(
                this,
                CHANNEL_ID
            )
                .setContentTitle(
                    "Bosha Live Translate Tube"
                )
                .setContentText(
                    "جاري التقاط صوت الفيديو"
                )
                .setSmallIcon(
                    android.R.drawable.ic_btn_speak_now
                )
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle(
                    "Bosha Live Translate Tube"
                )
                .setContentText(
                    "جاري التقاط صوت الفيديو"
                )
                .setSmallIcon(
                    android.R.drawable.ic_btn_speak_now
                )
                .build()
        }
    }

    override fun onDestroy() {
        isCapturing = false

        captureThread?.interrupt()
        captureThread = null

        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null

        mediaProjection?.stop()
        mediaProjection = null

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
