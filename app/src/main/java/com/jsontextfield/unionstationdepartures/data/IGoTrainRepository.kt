package com.jsontextfield.unionstationdepartures.data

import com.jsontextfield.unionstationdepartures.Train

interface IGoTrainRepository {
    suspend fun getTrains(): List<Train>
}