package com.example.homeworks.presentation

import android.app.Activity
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentImageBinding
import com.example.homeworks.utils.collectFlow
import com.example.homeworks.utils.compressBitmap
import com.example.homeworks.utils.compressUri
import com.example.homeworks.utils.loadImage

class ImageFragment : BaseFragment<FragmentImageBinding>(FragmentImageBinding::inflate) {
    private val viewModel: ImageViewModel by viewModels()

    override fun start() {
        setUpListeners()
        observe()
    }

    private fun setUpListeners() {
        binding.btnAddImage.setOnClickListener {
            showImagePicker()
        }
    }

    private fun observe() {
        collectFlow(viewModel.imageBitmap) { bitmap ->
            binding.imageView.loadImage(bitmap)
        }
    }

    private fun showImagePicker() {
        val bottomSheet = ImagePickerBottomSheet { isCamera ->
            if (isCamera) openCamera() else openGallery()
        }
        bottomSheet.show(parentFragmentManager, "ImagePickerBottomSheet")
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as Bitmap?
            bitmap?.let {
                val compressedBitmap = compressBitmap(it, 80)
                viewModel.setImageBitmap(compressedBitmap)

                showNotification("Image Set Successfully", "Your image was set successfully!")
            }
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let {
                val compressedBitmap = requireContext().compressUri(it, 80)
                compressedBitmap?.let { compressed ->
                    viewModel.setImageBitmap(compressed)
                    showNotification("Image Set Successfully", "Your image was set successfully!")
                }
            }
        }
    }


    private fun showNotification(title: String, message: String) {
        val notificationManager = requireContext().getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(requireContext(), "default_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(1, notification)
    }
}
