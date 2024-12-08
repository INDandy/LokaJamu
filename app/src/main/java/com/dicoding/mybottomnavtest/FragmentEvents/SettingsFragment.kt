package com.dicoding.mybottomnavtest.FragmentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybottomnavtest.MainActivity
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.databinding.FragmentSettingsBinding
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import com.dicoding.mybottomnavtest.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        progressBar = binding.progressBar

        val firstName = userViewModel.firstName.value
        val lastName = userViewModel.lastName.value
        val email = userViewModel.email.value

        if (firstName != null && lastName != null && email != null) {
            binding.newsName.text = "$firstName $lastName"
            binding.email.text = email
        } else {
            fetchUserDetails()
        }
        binding.logoutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        fetchUserDetails()

        return binding.root
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi Logout")
        builder.setMessage("Apakah kamu ingin logout?")

        builder.setPositiveButton("Yes") { _, _ ->
            logout()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun logout() {
        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            userPreference.logout()

            showToast("Anda berhasil logout")

            val activity = requireActivity() as MainActivity
            activity.logout()
        }
    }

    private fun fetchUserDetails() {
        showLoading(true)

        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(requireContext().dataStore)
            val token = userPreference.getToken()

            if (!token.isNullOrEmpty()) {
                try {
                    val apiService = ApiClient.getApiService()
                    val response = apiService.getUserDetails("Bearer $token")

                    if (response.isSuccessful) {
                        val firstName = response.body()?.data?.firstName
                        val lastName = response.body()?.data?.lastName
                        val email = response.body()?.data?.email

                        if (!firstName.isNullOrEmpty()) {
                            userViewModel.setUserDetails(firstName, lastName, email)
                            binding.newsName.text = "$firstName $lastName"
                            binding.email.text = email
                        } else {
                            showToast("User name is empty.")
                        }
                    } else {
                        showToast("Failed to fetch user details. Status: ${response.code()}")
                    }
                } catch (e: Exception) {
                    showToast("An error occurred: ${e.message}")
                } finally {
                    showLoading(false)
                }
            } else {
                showToast("Token is missing.")
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
