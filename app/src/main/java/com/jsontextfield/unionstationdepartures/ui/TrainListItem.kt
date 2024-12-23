package com.jsontextfield.unionstationdepartures.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jsontextfield.unionstationdepartures.Train

@Composable
fun TrainListItem(train: Train) {
    ListItem(
        headlineContent = { Text(train.destination) },
        supportingContent = { Text("Platform ${train.platform}") },
        leadingContent = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .width(40.dp)
                    .height(40.dp)
                    .background(color = Color(train.color))
            ) {

                Text(
                    train.name,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        },
        trailingContent = { Text(train.departureTime, maxLines = 2, textAlign = TextAlign.Center) }
    )
}

@Preview
@Composable
fun TrainListItemPreview() {
    val train = Train(
        destination = "Bloomington",
        platform = "4, 5",
        name = "RH",
    )
    TrainListItem(train)
}