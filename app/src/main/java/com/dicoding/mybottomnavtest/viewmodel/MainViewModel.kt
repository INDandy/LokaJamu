package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.mybottomnavtest.repository.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) = repository.register(firstName, lastName, email, password, confirmPassword)

}