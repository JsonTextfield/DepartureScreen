package com.jsontextfield.unionstationdepartures

import androidx.compose.ui.graphics.Color

data class Train(
    val code: String = "",
    val name: String = "",
    val destination: String = "",
    val platform: String = "",
    val departureTime: String = "",
    val color: Color = Color.Unspecified,
    val tripOrder: Int = 0
)
