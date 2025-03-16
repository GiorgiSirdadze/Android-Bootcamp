package com.example.homeworks.presentation.state

import com.example.homeworks.presentation.model.AuthInfo
import com.example.homeworks.presentation.model.RegisterInfo

data class RegisterState (
    val loader : Boolean = false,
    val error : String? = null,
    val registerInfo : RegisterInfo? = null
)