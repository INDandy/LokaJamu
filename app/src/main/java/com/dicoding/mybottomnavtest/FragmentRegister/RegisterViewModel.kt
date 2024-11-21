package com.dicoding.mybottomnavtest.FragmentRegister

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> get() = _registerState

    fun register(username: String,fullname : String, email: String, password: String) {
        if (username.isEmpty() || fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            _registerState.value = RegisterState.Error("All fields are required")
            return
        }

        _registerState.value = RegisterState.Loading

        // Simulate registration process (you should replace this with your actual implementation)
        // Example: Use a repository to make an API call here
        if (email.contains("@")) {
            _registerState.postValue(RegisterState.Success)
        } else {
            _registerState.postValue(RegisterState.Error("Invalid email format"))
        }
    }
}