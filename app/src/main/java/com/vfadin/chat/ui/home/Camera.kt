package com.vfadin.chat.ui.home

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.vfadin.chat.R

@Composable
fun Camera() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val provideCamera = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view
        }
    }

    AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize()) {
        provideCamera.addListener({
            val cameraProvider = provideCamera.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
            } catch (e: Exception) {
                Log.e("CamExc", "CameraException: ${e.localizedMessage}")
            }
        }, ContextCompat.getMainExecutor(context))
    }
}
