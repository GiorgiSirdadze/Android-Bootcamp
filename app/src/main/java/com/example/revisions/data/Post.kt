package com.example.revisions.data

data class PostItem(
    val id: Int,
    val images: List<String>?,
    val title: String,
    val comments: Int,
    val likes: Int,
    val shareContent: String,
    val owner: PostOwner
)

data class PostOwner(
    val fullName: String,
    val profile: String?,
    val postDate: String
)

