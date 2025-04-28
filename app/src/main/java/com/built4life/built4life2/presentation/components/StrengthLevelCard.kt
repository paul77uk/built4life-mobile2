package com.built4life.built4life2.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StrengthLevelCard(
    modifier: Modifier = Modifier,
    repMax: Int,
    eliteLevel: Int,
    beginnerLevel: Int = 0,
) {
    var barProgress = if (eliteLevel > 0) repMax.toFloat() / eliteLevel else 0f
    if (beginnerLevel > 0) barProgress = 1f - ((repMax.toFloat() - (eliteLevel -1)) / beginnerLevel)
    ProgressBar(
        modifier = Modifier
            .height(10.dp),
        barProgress = barProgress
    )
}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier,
    barProgress: Float,
    gradientColors: List<Color> = listOf(
        Color(0xFF0088FF),
        Color(0xFF80FF00),
        Color(0xFFFFFF00),
        Color(0xFFFF8000),
        Color(0xFFFF0000)
    )
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraSmall)
            .background(Color.LightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(barProgress)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.primary)
        )

    }

}