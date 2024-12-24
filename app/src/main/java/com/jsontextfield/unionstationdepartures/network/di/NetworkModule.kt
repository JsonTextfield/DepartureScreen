package com.jsontextfield.unionstationdepartures.network.di

import com.jsontextfield.unionstationdepartures.network.service.IGoTrainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @get:Provides
    @Singleton
    val service: IGoTrainService by lazy {
        val baseUrl = "https://api.openmetrolinx.com/OpenDataAPI/"
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(IGoTrainService::class.java)
    }
}