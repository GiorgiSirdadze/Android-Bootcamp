package com.example.exploreit.data.api

import kotlinx.serialization.Serializable

@Serializable
data class TourDto(
    val id: Int,
    val title: String,
    val location: String,
    val price: Int,
    val duration: String,
    val rating: Double,
    val image: String,
    val description: String
)

