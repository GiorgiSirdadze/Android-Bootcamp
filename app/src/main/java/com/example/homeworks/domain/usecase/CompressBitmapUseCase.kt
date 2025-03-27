package com.example.homeworks.domain.usecase

import android.graphics.Bitmap
import com.example.homeworks.domain.repository.ImageRepository
import com.example.homeworks.domain.resource.Resource
import javax.inject.Inject

interface CompressBitmapUseCase {
    suspend operator fun invoke(bitmap: Bitmap, quality: Int = 80): Resource<Bitmap>
}

class CompressBitmapUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : CompressBitmapUseCase {
    override suspend operator fun invoke(bitmap: Bitmap, quality: Int): Resource<Bitmap> =
        imageRepository.compressBitmap(bitmap, quality)
}