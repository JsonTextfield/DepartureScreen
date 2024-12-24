package com.jsontextfield.unionstationdepartures.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnionDeparturesResponse(
    @Json(name = "AllDepartures") val unionDepartures: UnionDepartures
) {
    @JsonClass(generateAdapter = true)
    data class UnionDepartures(
        @Json(name = "Trip") val trips: List<Trip>
    ) {
        @JsonClass(generateAdapter = true)
        data class Trip(
            @Json(name = "Info") val info: String,
            @Json(name = "TripNumber") val tripNumber: String,
            @Json(name = "Platform") val platform: String,
            @Json(name = "Service") val service: String,
            @Json(name = "ServiceType") val serviceType: String,
            @Json(name = "Time") val time: String
        )
    }
}