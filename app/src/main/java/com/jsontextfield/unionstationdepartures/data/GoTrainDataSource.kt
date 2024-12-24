package com.jsontextfield.unionstationdepartures.data

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.jsontextfield.unionstationdepartures.R
import com.jsontextfield.unionstationdepartures.Train
import com.jsontextfield.unionstationdepartures.network.model.NextServiceResponse
import com.jsontextfield.unionstationdepartures.network.model.UnionDeparturesResponse
import com.jsontextfield.unionstationdepartures.network.service.IGoTrainService
import com.jsontextfield.unionstationdepartures.ui.theme.Barrie
import com.jsontextfield.unionstationdepartures.ui.theme.Kitchener
import com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreEast
import com.jsontextfield.unionstationdepartures.ui.theme.LakeshoreWest
import com.jsontextfield.unionstationdepartures.ui.theme.Milton
import com.jsontextfield.unionstationdepartures.ui.theme.RichmondHill
import com.jsontextfield.unionstationdepartures.ui.theme.Stouffville
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GoTrainDataSource @Inject constructor(
    private val service: IGoTrainService,
    @ApplicationContext private val context: Context
) : IGoTrainDataSource {
    override suspend fun getTrains(): List<Train> {
        return try {
            val lines = service.getNextService(context.getString(R.string.go_key))
                .nextService.lines
            val trips = service.getUnionDepartures(context.getString(R.string.go_key))
                .unionDepartures.trips
            mergeLinesAndTrips(lines, trips)
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }

    private fun mergeLinesAndTrips(
        lines: List<NextServiceResponse.NextService.Line>,
        trips: List<UnionDeparturesResponse.UnionDepartures.Trip>
    ): List<Train> {
        val tripsMap = trips.associateBy { it.tripNumber }

        return lines.mapNotNull { line ->
            val matchingTrip = tripsMap[line.tripNumber]
            if (matchingTrip != null) {
                val color: Color = when (line.lineName) {
                    "Stouffville" -> Stouffville
                    "Richmond Hill" -> RichmondHill
                    "Milton" -> Milton
                    "Lakeshore West" -> LakeshoreWest
                    "Lakeshore East" -> LakeshoreEast
                    "Barrie" -> Barrie
                    "Kitchener" -> Kitchener
                    else -> Color(0xFF000000)
                }
                val inFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val outFormatter = DateTimeFormatter.ofPattern("HH:mm")
                val departureTimeString = LocalDateTime
                    .parse(matchingTrip.time, inFormatter)
                    .format(outFormatter)
                Train(
                    code = line.lineCode,
                    name = line.lineName,
                    destination = line.directionName.split(" - ").last(),
                    departureTime = departureTimeString,
                    platform = matchingTrip.platform,
                    color = color,
                    tripOrder = line.tripOrder,
                )
            }
            else {
                null
            }
        }.sortedWith(compareBy({ it.code }, { it.tripOrder }))
    }
}