package com.jsontextfield.unionstationdepartures.ui

import androidx.compose.ui.graphics.Color

enum class TrainLine(
    val code: String,
    val title: String,
    val colour: Color,
) {
    RichmondHill(
        code = "RH",
        title = "Richmond Hill",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.RichmondHill
    ),
    Kitchener(
        code = "KI",
        title = "Kitchener",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.Kitchener
    ),
    Barrie(
        code = "BR",
        title = "Barrie",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.Barrie
    ),
    LakeshoreWest(
        code = "LW",
        title = "Lakeshore West",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreWest
    ),
    LakeshoreEast(
        code = "LE",
        title = "Lakeshore East",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreEast
    ),
    Milton(
        code = "MI",
        title = "Milton",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.Milton
    ),
    Stouffville(
        code = "ST",
        title = "Stouffville",
        colour = com.jsontextfield.unionstationdepartures.ui.theme.Stouffville
    )
}