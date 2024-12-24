package com.jsontextfield.unionstationdepartures.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NextServiceResponse(
    @Json(name = "NextService") val nextService: NextService
) {
    @JsonClass(generateAdapter = true)
    data class NextService(
        @Json(name = "Lines") val lines: List<Line>
    ) {
        @JsonClass(generateAdapter = true)
        data class Line(
            @Json(name = "StopCode") val stopCode: String,
            @Json(name = "LineCode") val lineCode: String,
            @Json(name = "LineName") val lineName: String,
            @Json(name = "ServiceType") val serviceType: String,
            @Json(name = "DirectionCode") val directionCode: String,
            @Json(name = "DirectionName") val directionName: String,
            @Json(name = "ScheduledDepartureTime") val scheduledDepartureTime: String,
            @Json(name = "ComputedDepartureTime") val computedDepartureTime: String,
            @Json(name = "DepartureStatus") val departureStatus: String,
            @Json(name = "ScheduledPlatform") val scheduledPlatform: String,
            @Json(name = "ActualPlatform") val actualPlatform: String,
            @Json(name = "TripOrder") val tripOrder: Int,
            @Json(name = "TripNumber") val tripNumber: String,
            @Json(name = "UpdateTime") val updateTime: String,
            @Json(name = "Status") val status: String,
            @Json(name = "Latitude") val latitude: Double,
            @Json(name = "Longitude") val longitude: Double
        )
    }
}