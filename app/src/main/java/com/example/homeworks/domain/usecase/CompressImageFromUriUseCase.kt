package com.example.homeworks.domain.usecase

import android.graphics.Bitmap
import android.net.Uri
import com.example.homeworks.domain.repository.ImageRepository
import com.example.homeworks.domain.resource.Resource
import javax.inject.Inject

interface CompressImageFromUriUseCase {
    suspend operator fun invoke(uri: Uri, quality: Int = 80): Resource<Bitmap>
}

class CompressImageFromUriUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : CompressImageFromUriUseCase {
    override suspend operator fun invoke(uri: Uri, quality: Int): Resource<Bitmap> =
        imageRepository.compressImageFromUri(uri, quality)
}