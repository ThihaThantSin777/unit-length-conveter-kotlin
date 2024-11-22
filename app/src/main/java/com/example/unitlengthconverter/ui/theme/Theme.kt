package com.example.unitlengthconverter.ui.theme


import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// Define your colors
val Purple200 = Color(0xFFBB86FC)
val Teal200 = Color(0xFF03DAC5)

private val DarkColorPalette = darkColorScheme(
    primary = Purple200,
    secondary = Teal200
)

@Composable
fun UnitConverterTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        content = content
    )
}

