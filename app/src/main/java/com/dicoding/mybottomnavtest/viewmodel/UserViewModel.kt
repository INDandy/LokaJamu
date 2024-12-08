package com.dicoding.mybottomnavtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val _userDetailsStatus = MutableLiveData<Boolean>()
    val userDetailsStatus: LiveData<Boolean> get() = _userDetailsStatus
    val firstName = MutableLiveData<String?>()
    val lastName = MutableLiveData<String?>()
    val email = MutableLiveData<String?>()

    fun setUserDetails(firstName: String?, lastName: String?, email: String?) {
        this.firstName.value = firstName
        this.lastName.value = lastName
        this.email.value = email
    }
    fun setUserDetailsHome(firstName: String?) {
        this.firstName.value = firstName

    }

    fun setUserDetailsStatus(status: Boolean) {
        _userDetailsStatus.value = status
    }
}