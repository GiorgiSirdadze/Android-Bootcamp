package com.example.revisions.di

import com.example.revisions.data.ApiService
import com.example.revisions.repository.ItemRepository
import com.example.revisions.resource.ApiHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    @Provides
    fun provideApiHelper(): ApiHelper {
        return ApiHelper
    }

    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun provideItemRepository(
        apiHelper: ApiHelper,
        apiService: ApiService,

    ): ItemRepository {
        return ItemRepository(apiHelper, apiService)
    }
}