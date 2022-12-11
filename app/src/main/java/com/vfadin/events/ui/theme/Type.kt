package com.vfadin.events.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vfadin.events.R

val font = FontFamily(
    Font(R.font.inter)
)
// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = font,
    body1 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    overline = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    h1 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    h2 = TextStyle(
        fontFamily = font,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
)