package com.example.revisions.data

import android.annotation.SuppressLint


fun PostItemDto.toPresenter(): PostItem {
    return PostItem(
        id = this.id,
        images = this.images,
        title = this.title,
        comments = this.comments,
        likes = this.likes,
        shareContent = this.shareContent,
        owner = this.owner.toPresenter()
    )
}

fun PostOwnerDto.toPresenter(): PostOwner {
    return PostOwner(
        fullName = "${this.firstName} ${this.lastName}",
        profile = this.profile,
        postDate = formatDate(this.postDate)
    )
}

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("dd MMM yyyy HH:mm:ss")
    return formatter.format(date)
}
