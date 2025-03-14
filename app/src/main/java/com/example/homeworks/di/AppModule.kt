package com.example.homeworks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.homeworks.BuildConfig
import com.example.homeworks.data.remote.ApiService
import com.example.homeworks.data.local.UserDao
import com.example.homeworks.data.local.UserDatabase
import com.example.homeworks.data.repository.DataStoreRepositoryImpl
import com.example.homeworks.data.repository.LoginRepositoryImpl
import com.example.homeworks.data.repository.RegisterRepositoryImpl
import com.example.homeworks.data.repository.UserRepositoryImpl
import com.example.homeworks.resource.ApiHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideApiHelper(): ApiHelper{
        return ApiHelper
    }

    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiService {
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
    ): UserRepositoryImpl {
        return UserRepositoryImpl(apiHelper, apiService)
    }

    @Provides
    fun provideDataStoreRepository(
        dataStore: DataStore<Preferences>
    ):DataStoreRepositoryImpl{
        return DataStoreRepositoryImpl(dataStore)
    }
    @Provides
    fun provideLoginRepository(
        apiHelper: ApiHelper,
        apiService: ApiService,
    ):LoginRepositoryImpl{
        return LoginRepositoryImpl(apiHelper,apiService)
    }

    @Provides
    fun provideRegisterRepository(
        apiHelper: ApiHelper,
        apiService: ApiService,
    ): RegisterRepositoryImpl {
        return RegisterRepositoryImpl(apiHelper,apiService)
    }
}
