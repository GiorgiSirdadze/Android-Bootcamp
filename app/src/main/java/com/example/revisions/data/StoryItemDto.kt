package com.example.revisions.data

import kotlinx.serialization.Serializable

@Serializable
data class StoryItemDto(
    val id: Int,
    val cover: String,
    val title: String
)

