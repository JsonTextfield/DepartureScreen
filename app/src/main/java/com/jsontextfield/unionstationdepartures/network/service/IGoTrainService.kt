package com.jsontextfield.unionstationdepartures.network.service

import com.jsontextfield.unionstationdepartures.network.model.NextServiceResponse
import com.jsontextfield.unionstationdepartures.network.model.UnionDeparturesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface IGoTrainService {
    @GET("api/V1/Stop/NextService/UN")
    suspend fun getNextService(@Query("key") apiKey: String): NextServiceResponse

    @GET("api/V1/ServiceUpdate/UnionDepartures/All")
    suspend fun getUnionDepartures(@Query("key") apiKey: String): UnionDeparturesResponse
}