package com.example.revisions.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val status: Boolean,
    val users: List<User>
)

@Serializable
data class User(
    val id: Int,

    @SerialName("avatar")
    val avatar: String? = null,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("about")
    val about: String? = null,

    @SerialName("activation_status")
    val activationStatus: Double
)
