package com.example.homeworks.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homeworks.api.ApiService
import com.example.homeworks.local.UserDao
import com.example.homeworks.local.UserDatabase
import com.example.homeworks.repository.UserRepository
import com.example.homeworks.resource.ApiHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    @Provides
    fun provideApiHelper(): ApiHelper{
        return ApiHelper
    }

    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService{
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    fun provideUserRepository(
        apiHelper: ApiHelper,
        apiService: ApiService,
        userDao: UserDao
    ): UserRepository {
        return UserRepository(apiHelper, apiService, userDao)
    }
}