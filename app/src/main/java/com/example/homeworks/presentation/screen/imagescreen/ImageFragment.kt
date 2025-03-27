package com.example.homeworks.presentation.screen.imagescreen

import android.app.Activity
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import com.example.homeworks.R
import com.example.homeworks.databinding.FragmentImageBinding
import com.example.homeworks.presentation.BaseFragment
import com.example.homeworks.presentation.screen.ImagePickerBottomSheet
import com.example.homeworks.utils.collectFlow
import com.example.homeworks.utils.loadImage
import com.example.homeworks.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : BaseFragment<FragmentImageBinding>(FragmentImageBinding::inflate) {
    private val viewModel: ImageViewModel by viewModels()

    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun start() {
        setUpListeners()
        observeState()
        observeEvents()
        setupActivityResults()
    }

    private fun setUpListeners() {
        binding.btnAddImage.setOnClickListener {
            viewModel.onEvent(ImageEvent.ShowImagePicker)
        }
        binding.btnUploadImage.setOnClickListener {
            viewModel.onEvent(ImageEvent.UploadImage)
        }
    }

    private fun observeState() {
        collectFlow(viewModel.state) { state ->
            state.imageBitmap?.let { bitmap ->
                binding.imageView.loadImage(bitmap)
            }
        }
    }

    private fun observeEvents() {
        collectFlow(viewModel.uiEvents) { event ->
            when (event) {
                is ImageUiEvents.ShowBottomSheet -> showImagePicker()
                is ImageUiEvents.OpenCamera -> openCamera()
                is ImageUiEvents.OpenGallery -> openGallery()
                is ImageUiEvents.ShowNotification ->
                    showNotification(event.title, event.message)
                is ImageUiEvents.ShowError ->
                    binding.root.showSnackBar(event.error)
            }
        }
    }

    private fun setupActivityResults() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as Bitmap?
                viewModel.onEvent(ImageEvent.CameraResult(bitmap))
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                viewModel.onEvent(ImageEvent.GalleryResult(uri))
            }
        }
    }

    private fun showImagePicker() {
        val bottomSheet = ImagePickerBottomSheet { isCamera ->
            viewModel.onEvent(ImageEvent.ImageSourceSelected(isCamera))
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