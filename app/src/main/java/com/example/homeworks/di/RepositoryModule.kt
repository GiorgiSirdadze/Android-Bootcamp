package com.example.homeworks.di

import com.example.homeworks.data.repository.CategoryRepositoryImpl
import com.example.homeworks.domain.repository.CategoryRepository
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
    abstract fun bindCategoryRepository(
        repository : CategoryRepositoryImpl
    ): CategoryRepository
}
