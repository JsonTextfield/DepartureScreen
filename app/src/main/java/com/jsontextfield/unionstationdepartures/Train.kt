package com.jsontextfield.unionstationdepartures

data class Train(
    val name: String = "",
    val destination: String = "",
    val platform: String = "",
    val departureTime: String = "",
    val color: Int = 0,
)
