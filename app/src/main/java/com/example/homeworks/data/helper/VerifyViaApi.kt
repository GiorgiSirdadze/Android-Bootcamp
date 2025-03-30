package com.example.homeworks.data.helper

import com.example.homeworks.data.remote.VerificationResponeDto
import com.example.homeworks.domain.model.AccountVerificationResult
import com.example.homeworks.domain.resource.ApiHelper
import com.example.homeworks.domain.resource.Resource
import kotlinx.coroutines.flow.first
import retrofit2.Response

suspend fun verifyViaApi(
    apiCall: suspend () -> Response<VerificationResponeDto>
): AccountVerificationResult {
    return try {
        val result = ApiHelper.handleHttpRequest { apiCall() }
            .first { it is Resource.Success || it is Resource.Error }

        when (result) {
            is Resource.Success -> {
                AccountVerificationResult(
                    isValid = result.data.status == "Success",
                    errorMessage = if (result.data.status != "Success") "Invalid" else null
                )
            }
            is Resource.Error -> {
                AccountVerificationResult(isValid = false, errorMessage = result.errorMessage)
            }
            else -> throw IllegalStateException("Unexpected state")
        }
    } catch (e: Exception) {
        AccountVerificationResult(isValid = false, errorMessage = "Network error")
    }
}