package com.vfadin.events.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CaptchaImage(model: String, onClick: () -> Unit) {
    val url = model.replace("\\", "")
    GlideImage(
        modifier = Modifier
            .height(60.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        imageModel = url,
        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds)
    )
}