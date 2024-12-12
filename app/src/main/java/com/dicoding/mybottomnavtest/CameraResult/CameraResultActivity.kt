package com.dicoding.mybottomnavtest.CameraResult

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.databinding.ActivityCameraResultBinding
import com.dicoding.mybottomnavtest.modelresponse.ModelResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CameraResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("imagePath")

        if (imagePath != null) {
            Glide.with(this)
                .load(imagePath)
                .into(binding.imageView)
            processImage(imagePath)
        } else {
            binding.imageView.setImageResource(R.drawable.ic_placeholder)
        }
    }

    private fun processImage(imagePath: String?) {
        if (imagePath.isNullOrEmpty()) {
            showError("Image is required")
            return
        }

        val file = File(imagePath)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val apiModel = ApiClient.ModelApiService()

        lifecycleScope.launch {
            try {
                val response = apiModel.getModel(body)
                if (response.isSuccessful) {
                    val modelResponse = response.body()
                    if (modelResponse?.status == "success") {
                        val data = Bundle().apply {
                            putString("name", modelResponse.data?.spice?.name)
                            putString("description", modelResponse.data?.spice?.description)
                            putString("benefits", modelResponse.data?.spice?.benefits)
                            putStringArrayList(
                                "jamulist",
                                ArrayList(modelResponse.data?.spice?.jamuList ?: emptyList())
                            )
                        }
                        showContent(data)
                    } else if (modelResponse?.status == "fail") {
                        handleApiError(modelResponse)
                    }
                } else {
                    when (response.code()) {
                        422 -> showError("422 No prediction could be made for the provided image. Try another image.")
                        400 -> {
                            // Check if the error is related to missing or invalid image format
                            val errorMessage = when {
                                response.message()
                                    .contains("Image is required", ignoreCase = true) -> {
                                    "400 Image is required"
                                }

                                response.message().contains(
                                    "Only jpg, jpeg, and png images are allowed",
                                    ignoreCase = true
                                ) -> {
                                    "400 if image format is except jpg, jpeg, png\nOnly jpg, jpeg, and png images are allowed"
                                }

                                else -> "Error: ${response.code()} - ${response.message()}"
                            }
                            showError(errorMessage)
                        }

                        else -> showError("Error: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }
        }
    }




        private fun handleApiError(modelResponse: ModelResponse) {
        val errorMessage = StringBuilder()

        if (!modelResponse.errors.isNullOrEmpty()) {
            for (error in modelResponse.errors) {
                errorMessage.append("Field: ${error.field}, Message: ${error.message}\n")
            }
        } else {
            errorMessage.append(modelResponse.message)
        }

        showError(errorMessage.toString())
    }

    private fun showContent(data: Bundle?) {
        val bottomSheet = BottomSheetCameraResult(data)
        bottomSheet.isCancelable = false
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}


