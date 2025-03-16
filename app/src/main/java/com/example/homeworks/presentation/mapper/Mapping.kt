package com.example.homeworks.presentation.mapper

import com.example.homeworks.data.remote.LoginResponseDto
import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.domain.model.RegisterResponse
import com.example.homeworks.presentation.model.AuthInfo
import com.example.homeworks.presentation.model.RegisterInfo

fun LoginResponse.toPresentation(): AuthInfo {
    return AuthInfo(
        token = this.token
    )
}

fun RegisterResponse.toPresentation() : RegisterInfo {
    return RegisterInfo(
        token = this.token
    )
}