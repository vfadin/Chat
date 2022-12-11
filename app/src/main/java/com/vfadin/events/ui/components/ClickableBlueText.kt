package com.vfadin.events.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vfadin.events.R
import com.vfadin.events.ui.theme.font

@Composable
fun ClickableBlueText(text: String, modifier: Modifier, onClick: () -> Unit = {}) {
    androidx.compose.foundation.text.ClickableText(
        modifier = modifier,
        text = buildAnnotatedString {
            append(text)
        },
        onClick = { onClick() },
        style = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = colorResource(R.color.blue)
        )
    )
}