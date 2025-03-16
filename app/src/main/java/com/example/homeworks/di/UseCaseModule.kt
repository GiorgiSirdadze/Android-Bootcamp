package com.example.homeworks.di

import com.example.homeworks.domain.usecase.ClearDataUseCase
import com.example.homeworks.domain.usecase.ClearDataUseCaseImpl
import com.example.homeworks.domain.usecase.GetDataUseCase
import com.example.homeworks.domain.usecase.GetDataUseCaseImpl
import com.example.homeworks.domain.usecase.LoginUseCase
import com.example.homeworks.domain.usecase.LoginUseCaseImpl
import com.example.homeworks.domain.usecase.RegisterUseCase
import com.example.homeworks.domain.usecase.RegisterUseCaseImpl
import com.example.homeworks.domain.usecase.SaveDataUseCase
import com.example.homeworks.domain.usecase.SaveDataUseCaseImpl
import com.example.homeworks.domain.usecase.ValidateEmailUseCase
import com.example.homeworks.domain.usecase.ValidateEmailUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindValidateEmailUseCase(
        useCase: ValidateEmailUseCaseImpl
    ): ValidateEmailUseCase

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        useCase: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    @Singleton
    abstract fun bindRegisterUseCase(
        useCase : RegisterUseCaseImpl
    ) : RegisterUseCase

    @Binds
    @Singleton
    abstract fun bindSaveDataUseCase(
        useCase : SaveDataUseCaseImpl
    ): SaveDataUseCase

    @Binds
    @Singleton
    abstract fun bindGetDataUseCase(
        useCase : GetDataUseCaseImpl
    ): GetDataUseCase

    @Binds
    @Singleton
    abstract fun bindClearDataUseCase(
        useCase : ClearDataUseCaseImpl
    ): ClearDataUseCase
}
