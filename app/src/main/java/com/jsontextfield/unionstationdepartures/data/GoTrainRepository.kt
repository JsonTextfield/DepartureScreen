package com.jsontextfield.unionstationdepartures.data

import com.jsontextfield.unionstationdepartures.Train
import javax.inject.Inject

class GoTrainRepository @Inject constructor(
    private val goTrainDataSource: IGoTrainDataSource,
) : IGoTrainRepository {
    override suspend fun getTrains(): List<Train> = goTrainDataSource.getTrains()
}