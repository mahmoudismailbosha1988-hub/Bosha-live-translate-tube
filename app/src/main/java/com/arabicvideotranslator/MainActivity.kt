package com.arabicvideotranslator

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.arabicvideotranslator.audio.AudioCaptureService

class MainActivity : ComponentActivity() {

    private lateinit var mediaProjectionManager: MediaProjectionManager

    companion object {
        private const val REQUEST_MEDIA_PROJECTION = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        setContent {
            ArabicVideoTranslatorApp(
                onStartTranslation = {
                    requestMediaProjection()
                },
                onStopTranslation = {
                    stopAudioCapture()
                }
            )
        }
    }

    private fun requestMediaProjection() {
        val captureIntent =
            mediaProjectionManager.createScreenCaptureIntent()

        startActivityForResult(
            captureIntent,
            REQUEST_MEDIA_PROJECTION
        )
    }

    @Deprecated("Used to receive MediaProjection permission result")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (
            requestCode == REQUEST_MEDIA_PROJECTION &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            val serviceIntent =
                Intent(this, AudioCaptureService::class.java).apply {
                    putExtra(
                        AudioCaptureService.EXTRA_RESULT_CODE,
                        resultCode
                    )
                    putExtra(
                        AudioCaptureService.EXTRA_RESULT_DATA,
                        data
                    )
                }

            ContextCompat.startForegroundService(
                this,
                serviceIntent
            )
        }
    }

    private fun stopAudioCapture() {
        val serviceIntent =
            Intent(this, AudioCaptureService::class.java)

        stopService(serviceIntent)
    }
}

@Composable
fun ArabicVideoTranslatorApp(
    onStartTranslation: () -> Unit,
    onStopTranslation: () -> Unit
) {
    var isTranslating by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(
                id = com.arabicvideotranslator.R.drawable.bosha_background
            ),
            contentDescription = "Amal",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Bosha Live Translate Tube"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {
                    isTranslating = true
                    onStartTranslation()
                }
            ) {
                Text(
                    text = if (isTranslating) {
                        "الترجمة تعمل"
                    } else {
                        "بدء الترجمة"
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    isTranslating = false
                    onStopTranslation()
                }
            ) {
                Text("إيقاف الترجمة")
            }
        }
    }
}
