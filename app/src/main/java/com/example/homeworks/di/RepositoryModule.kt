package com.example.homeworks.di

import com.example.homeworks.data.repository.FromAccountRepositoryImpl
import com.example.homeworks.data.repository.ToAccountRepositoryImpl
import com.example.homeworks.domain.repository.FromAccountRepository
import com.example.homeworks.domain.repository.ToAccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFromAccountRepository(
        fromAccountRepositoryImpl: FromAccountRepositoryImpl
    ): FromAccountRepository

    @Binds
    @Singleton
    abstract fun bindToAccountRepository(
        toAccountRepositoryImpl: ToAccountRepositoryImpl
    ): ToAccountRepository


}