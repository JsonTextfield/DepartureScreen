package com.jsontextfield.unionstationdepartures.di

import android.content.Context
import com.jsontextfield.unionstationdepartures.data.GoTrainDataSource
import com.jsontextfield.unionstationdepartures.data.GoTrainRepository
import com.jsontextfield.unionstationdepartures.data.IGoTrainDataSource
import com.jsontextfield.unionstationdepartures.data.IGoTrainRepository
import com.jsontextfield.unionstationdepartures.network.service.IGoTrainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideGoTrainDataSource(
        goTrainService: IGoTrainService,
        @ApplicationContext context: Context
    ): IGoTrainDataSource {
        return GoTrainDataSource(goTrainService, context)
    }

    @Provides
    fun provideGoTrainRepository(goTrainDataSource: IGoTrainDataSource): IGoTrainRepository {
        return GoTrainRepository(goTrainDataSource)
    }
}