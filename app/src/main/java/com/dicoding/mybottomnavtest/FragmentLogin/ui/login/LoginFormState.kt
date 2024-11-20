package com.dicoding.mybottomnavtest.FragmentLogin.ui.login

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val loginError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)