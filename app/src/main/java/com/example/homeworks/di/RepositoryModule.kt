package com.example.homeworks.di

import android.content.Context
import com.example.homeworks.data.repository.ImageRepositoryImpl
import com.example.homeworks.data.repository.StorageRepositoryImpl
import com.example.homeworks.domain.repository.ImageRepository
import com.example.homeworks.domain.repository.StorageRepository
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideImageRepository(
        @ApplicationContext context: Context,
        ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ): ImageRepository = ImageRepositoryImpl(context, ioDispatcher)

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    fun provideStorageRepository(storage: FirebaseStorage): StorageRepository {
        return StorageRepositoryImpl(storage)
    }
}