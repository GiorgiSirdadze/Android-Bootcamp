package com.example.homeworks.presentation.state

import com.example.homeworks.presentation.model.AuthInfo

data class LoginState (
    val loader : Boolean = false,
    val error : String? = null,
    val authInfo : AuthInfo? = null
)
