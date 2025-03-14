package com.example.homeworks.data.mapper

import com.example.homeworks.data.local.UserEntity
import com.example.homeworks.data.remote.DataDto
import com.example.homeworks.data.remote.LoginResponseDto
import com.example.homeworks.data.remote.RegisterResponseDto
import com.example.homeworks.data.remote.SupportDto
import com.example.homeworks.data.remote.UserDto
import com.example.homeworks.domain.model.Data
import com.example.homeworks.domain.model.LoginResponse
import com.example.homeworks.domain.model.RegisterResponse
import com.example.homeworks.domain.model.Support
import com.example.homeworks.domain.model.User
import com.example.homeworks.domain.model.UserEnt

fun UserDto.toDomain(): User {
    return User(
        page = this.page,
        perPage = this.perPage,
        total = this.total,
        totalPages = this.totalPages,
        data = this.data.map { it.toDomain() },
        support = this.support.toDomain()
    )
}

fun DataDto.toDomain(): Data {
    return Data(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}

fun SupportDto.toDomain(): Support {
    return Support(
        url = this.url,
        text = this.text
    )
}

fun UserEntity.toDomain(): UserEnt {
    return UserEnt(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        avatar = this.avatar
    )
}

fun LoginResponseDto.toDomain(): LoginResponse{
    return LoginResponse(
        token = this.token
    )
}

fun RegisterResponseDto.toDomain() : RegisterResponse {
    return RegisterResponse(
        token = this.token
    )

}