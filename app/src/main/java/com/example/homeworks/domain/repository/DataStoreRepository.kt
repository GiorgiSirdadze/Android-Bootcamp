package com.example.homeworks.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> saveData(key: Preferences.Key<T>, value: T)
    suspend fun clearData()
    fun <T> getData(key: Preferences.Key<T>, defaultValue: T): Flow<T>
}
