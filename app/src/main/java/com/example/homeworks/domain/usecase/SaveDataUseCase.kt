package com.example.homeworks.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.homeworks.domain.repository.DataStoreRepository
import javax.inject.Inject

interface SaveDataUseCase {
    suspend operator fun <T> invoke(key : Preferences.Key<T>, value : T)
}

class SaveDataUseCaseImpl @Inject constructor(
    private val dataStoreRepository : DataStoreRepository
):SaveDataUseCase{
    override suspend operator fun <T> invoke (key : Preferences.Key<T>, value : T){
        dataStoreRepository.saveData(key = key, value = value)
    }
}