package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val firstName = MutableLiveData<String?>()
    val lastName = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()

    // Fungsi untuk menyimpan detail user ke ViewModel
    fun setUserDetails(firstName: String?, lastName: String?, email: String?) {
        this.firstName.value = firstName
        this.lastName.value = lastName
        this.email.value = email
    }
    fun setUserDetailsHome(firstName: String?) {
        this.firstName.value = firstName

    }
}