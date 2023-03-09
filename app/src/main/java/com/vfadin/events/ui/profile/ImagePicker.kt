package com.vfadin.events.ui.profile

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vfadin.events.R
import com.vfadin.events.ui.theme.Primary
import com.vfadin.events.ui.theme.White
import java.io.File

class ComposeFileProvider : FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()
            val file = File.createTempFile("selected_image_", ".jpg", directory)
            val authority = context.packageName + ".provider"
            return getUriForFile(context, authority, file)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    onFilePicked: (Uri) -> Unit,
) {
    val emptyImageUri = Uri.parse("file://dev/null")
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf(emptyImageUri) }
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )
    val imageCropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                onFilePicked(result.uriContent ?: emptyImageUri)
            }
        })
    val context = LocalContext.current
    Box(
        modifier = Modifier.then(modifier),
    ) {
        if (hasImage && imageUri != emptyImageUri) {
            LaunchedEffect(Unit) {
                val cropOptions = CropImageContractOptions(imageUri,
                    CropImageOptions(
                        cropShape = CropImageView.CropShape.OVAL,
                        fixAspectRatio = true,
                        autoZoomEnabled = true
                    )
                )
                imageCropLauncher.launch(cropOptions)
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(White)
                        .clickable { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gallery_line),
                        contentDescription = null,
                        tint = Primary
                    )
                }
                Text(text = "Выбрать", color = Primary)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(White)
                        .clickable {
                            if (cameraPermissionState.status.isGranted) {
                                val uri = ComposeFileProvider.getImageUri(context)
                                imageUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(19.dp),
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        tint = Primary
                    )
                }
                Text(text = "Камера", color = Primary)
            }
        }
    }
}