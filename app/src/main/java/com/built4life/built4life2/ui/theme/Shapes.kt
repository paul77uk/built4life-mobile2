package com.built4life.built4life2.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

data class Shapes(
    val button: Shape = RoundedCornerShape(12.dp),
)

val LocalShapes = compositionLocalOf { Shapes() }
