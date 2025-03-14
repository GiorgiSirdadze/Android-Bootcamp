package com.example.homeworks.di

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
        impl: ValidateEmailUseCaseImpl
    ): ValidateEmailUseCase
}
