package com.example.homeworks.domain.usecase

import com.example.homeworks.domain.repository.DataStoreRepository
import javax.inject.Inject

interface ClearDataUseCase {
    suspend operator fun invoke()
}
class ClearDataUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ClearDataUseCase {
    override suspend operator fun invoke() {
        dataStoreRepository.clearData()
    }
}
