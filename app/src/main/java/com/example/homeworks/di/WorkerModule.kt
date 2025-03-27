package com.example.homeworks.di

import android.content.Context
import androidx.work.WorkManager
import com.example.homeworks.data.worker.WorkerManagerHelperImpl
import com.example.homeworks.domain.repository.StorageRepository
import com.example.homeworks.domain.worker.WorkerManagerHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    fun provideWorkerManagerHelper(workManager: WorkManager): WorkerManagerHelper {
        return WorkerManagerHelperImpl(workManager)
    }



}