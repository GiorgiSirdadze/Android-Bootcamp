package com.example.homeworks.di

import com.example.homeworks.data.repository.DataStoreRepositoryImpl
import com.example.homeworks.domain.repository.LoginRepository
import com.example.homeworks.data.repository.LoginRepositoryImpl
import com.example.homeworks.data.repository.RegisterRepositoryImpl
import com.example.homeworks.domain.repository.DataStoreRepository
import com.example.homeworks.domain.repository.RegisterRepository
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
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        repository: DataStoreRepositoryImpl
    ): DataStoreRepository
}
