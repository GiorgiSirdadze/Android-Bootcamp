package com.example.revisions.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val avatar: String? = null,
    val firstName: String,
    val lastName: String,
    val about: String? = null,
    val activationStatus: Double
)


