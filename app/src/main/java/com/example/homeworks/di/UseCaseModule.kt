package com.example.homeworks.di
import com.example.homeworks.domain.usecase.GetAccountsUseCase
import com.example.homeworks.domain.usecase.GetAccountsUseCaseImpl
import com.example.homeworks.domain.usecase.VerifyAccountsUseCase
import com.example.homeworks.domain.usecase.VerifyAccountsUseCaseImpl
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
    abstract fun bindGetAccountsUseCase(
        useCase: GetAccountsUseCaseImpl
    ): GetAccountsUseCase

    @Binds
    @Singleton
    abstract fun bindVerifyAccountsUseCase(
        useCase: VerifyAccountsUseCaseImpl
    ): VerifyAccountsUseCase
}