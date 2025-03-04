package com.example.exploreit.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.exploreit.R

fun ImageView.loadWithErrorHandling(
    imageUrl: String?,
    placeholderResId: Int = R.drawable.ic_launcher_background,
    errorResId: Int = R.drawable.ic_launcher_background,
    onError: (Throwable) -> Unit = {}
) {
    Glide.with(context)
        .load(imageUrl)
        .apply(RequestOptions().placeholder(placeholderResId).error(errorResId))
        .into(object : com.bumptech.glide.request.target.ImageViewTarget<Drawable>(this) {
            override fun setResource(resource: Drawable?) {
                view.setImageDrawable(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                onError(Exception("Image load failed"))
            }
        })
}
