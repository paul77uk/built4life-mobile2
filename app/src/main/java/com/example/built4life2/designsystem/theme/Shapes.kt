package com.example.built4life2.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class Shapes(
    val button: Shape = RoundedCornerShape(12.dp),
)

val LocalShapes = compositionLocalOf { Shapes() }
