package com.example.homeworks.utils

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.homeworks.R

fun AppCompatImageView.loadImage(uri: Uri?) {
    Glide.with(context)
        .load(uri)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}

fun AppCompatImageView.loadImage(bitmap: Bitmap?) {
    bitmap?.let { setImageBitmap(it) }
}