package com.arabicvideotranslator

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                    // سيتم ربط إيقاف الترجمة هنا لاحقًا
                }
            )
        }
    }

    private fun requestMediaProjection() {
        val captureIntent = mediaProjectionManager.createScreenCaptureIntent()
        startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION)
    }

    @Deprecated("يُستخدم حاليًا لاستلام نتيجة إذن MediaProjection")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // سيتم تمرير إذن التقاط الصوت إلى الخدمة في الخطوة التالية
            }
        }
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
            Text(
                text = "إيقاف الترجمة"
            )
        }
    }
}
