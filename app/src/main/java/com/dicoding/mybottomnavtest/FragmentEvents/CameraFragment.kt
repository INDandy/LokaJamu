package com.dicoding.mybottomnavtest.FragmentEvents

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dicoding.mybottomnavtest.CameraResult.CameraResultActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.FragmentCameraBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageCapture: ImageCapture
    private var cameraProvider: ProcessCameraProvider? = null
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private val multiplePermissionId = 14

    private val multiplePermissionNameList = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.CAMERA)
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCameraBinding.bind(view)

        // Setup UI
        setupView()

        // Check permissions
        if (checkMultiplePermissions()) {
            startCamera()
        } else {
            requestPermissions(multiplePermissionNameList, multiplePermissionId)
        }

        // Capture Button Click Listener
        binding.captureButton.setOnClickListener {
            takePhoto()
        }

        // Flash Toggle Click Listener
        binding.flashToggleIB.setOnClickListener {
            toggleFlash()
        }
    }

    private fun checkMultiplePermissions(): Boolean {
        return multiplePermissionNameList.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error initializing camera: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageFolder = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Images"
        )
        if (!imageFolder.exists()) {
            imageFolder.mkdirs()
        }

        val fileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis()) + ".jpg"
        val photoFile = File(imageFolder, fileName)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    navigateToCameraResult(photoFile.absolutePath)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "Failed to save photo", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun navigateToCameraResult(imagePath: String) {
        val intent = Intent(requireContext(), CameraResultActivity::class.java).apply {
            putExtra("imagePath", imagePath)
        }
        startActivity(intent)
    }

    private fun toggleFlash() {
        val camera = cameraProvider?.bindToLifecycle(
            viewLifecycleOwner,
            CameraSelector.Builder().requireLensFacing(lensFacing).build()
        )

        if (camera != null && camera.cameraInfo.hasFlashUnit()) {
            val isTorchOn = camera.cameraInfo.torchState.value == TorchState.ON
            camera.cameraControl.enableTorch(!isTorchOn)
            val icon = if (isTorchOn) R.drawable.flash_off else R.drawable.flash_on
            binding.flashToggleIB.setImageResource(icon)
        } else {
            Toast.makeText(requireContext(), "Flash is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupView() {
        // Hide status bar and action bar
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requireActivity().actionBar?.hide()

        // Hide Bottom Navigation and FloatingActionButton
        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar?.visibility = View.GONE

        val kamera = requireActivity().findViewById<FloatingActionButton>(R.id.kamera)
        kamera?.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
