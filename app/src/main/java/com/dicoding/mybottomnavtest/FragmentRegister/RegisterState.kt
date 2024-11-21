package com.dicoding.mybottomnavtest.FragmentRegister

sealed class RegisterState {
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
    object Loading : RegisterState()
}