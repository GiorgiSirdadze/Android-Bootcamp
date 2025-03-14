package com.example.homeworks.domain.usecase

import androidx.datastore.preferences.core.Preferences
import com.example.homeworks.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetDataUseCase {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T>
}

class GetDataUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : GetDataUseCase {
    override fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStoreRepository.getData(key = key, defaultValue = defaultValue)

}