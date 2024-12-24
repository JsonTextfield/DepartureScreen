package com.jsontextfield.unionstationdepartures.data

import com.jsontextfield.unionstationdepartures.Train

interface IGoTrainDataSource {
    suspend fun getTrains(): List<Train>
}
