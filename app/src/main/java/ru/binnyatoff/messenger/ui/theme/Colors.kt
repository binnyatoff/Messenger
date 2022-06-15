package ru.binnyatoff.messenger.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val primaryTintColor: Color,
    val primaryTextColor: Color,
    val secondaryBackground: Color,
    val actionTextColor: Color

)

val lightPallete = Colors(
    primaryBackground = Color.White,
    primaryTextColor = Color(0xFF000000),
    primaryTintColor = Color(0xFFFF8A00),
    secondaryBackground = Color(0x10D0CCC7),
    actionTextColor = Color(0xFF065cbf)
)