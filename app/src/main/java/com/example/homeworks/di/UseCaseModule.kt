package com.example.homeworks.di

import com.example.homeworks.domain.repository.ImageRepository
import com.example.homeworks.domain.usecase.CompressBitmapUseCase
import com.example.homeworks.domain.usecase.CompressBitmapUseCaseImpl
import com.example.homeworks.domain.usecase.CompressImageFromUriUseCase
import com.example.homeworks.domain.usecase.CompressImageFromUriUseCaseImpl
import com.example.homeworks.domain.usecase.UploadImageUseCase
import com.example.homeworks.domain.usecase.UploadImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindCompressBitmapUseCase(
        useCase: CompressBitmapUseCaseImpl
    ): CompressBitmapUseCase

    @Binds
    @Singleton
    abstract fun bindCompressImageFromUriUseCase(
        useCase: CompressImageFromUriUseCaseImpl
    ): CompressImageFromUriUseCase

    @Binds
    @Singleton
    abstract fun bindUploadImageUseCase(
        useCase: UploadImageUseCaseImpl
    ): UploadImageUseCase
}