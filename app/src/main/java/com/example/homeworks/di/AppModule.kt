package com.example.homeworks.di

import com.example.homeworks.BuildConfig
import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.domain.resource.ApiHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
   @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(logging:HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
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

}
