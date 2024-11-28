package com.dicoding.mybottomnavtest.FragmentLogin


import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybottomnavtest.ForgetPassword.ForgetFragment
import com.dicoding.mybottomnavtest.FragmentEvents.HomeFragment
import com.dicoding.mybottomnavtest.FragmentRegister.RegisterFragment
import com.dicoding.mybottomnavtest.MainActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.UserModel
import com.dicoding.mybottomnavtest.ViewModelFactory
import com.dicoding.mybottomnavtest.customviewmodel.emailview
import com.dicoding.mybottomnavtest.customviewmodel.passwordview
import com.dicoding.mybottomnavtest.databinding.FragmentLoginBinding
import com.dicoding.mybottomnavtest.preference.ResultValue
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import com.dicoding.mybottomnavtest.viewmodel.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(requireContext()) }
    private lateinit var binding: FragmentLoginBinding
    private lateinit var myButton: Button
    private lateinit var passwordEditText: passwordview
    private lateinit var emailEditText: emailview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        val forgotPassword = view.findViewById<TextView>(R.id.forgotPassword)

        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        myButton = binding.loginButton

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        forgotPassword.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            transaction.replace(R.id.frame_layout, ForgetFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.createAccountLink.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
            )
            transaction.replace(R.id.frame_layout, RegisterFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        setupView()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        requireActivity().actionBar?.hide()

        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar?.visibility = View.GONE

        val kamera = requireActivity().findViewById<FloatingActionButton>(R.id.kamera)
        kamera?.visibility = View.GONE
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        viewModel.login(email, password).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is ResultValue.Loading -> {
                        showLoading(true)
                    }
                    is ResultValue.Success -> {
                        val token = result.data.toString()
                        val user = UserModel(email, password, token)

                        lifecycleScope.launch {
                            val userPreference = UserPreference.getInstance(requireContext().dataStore)
                            userPreference.saveSession(user)
                        }
                        showLoading(false)
                        navigateToHome()
                    }
                    is ResultValue.Error -> {
                        val errorMessage = result.error
                        showToast("An error occurred: $errorMessage")
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setMyButton() {
        val emailResult = emailEditText.text.toString().isNotEmpty() && emailEditText.error == null
        val passwordResult = passwordEditText.text.toString().isNotEmpty() && passwordEditText.error == null
        if (emailResult && passwordResult) {
            myButton.isEnabled = true
            myButton.setOnClickListener { login() }
        } else {
            myButton.isEnabled = false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToHome() {
        val email = binding.emailEditText.text.toString()

        showWelcomeDialog(email)

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, HomeFragment())
        transaction.commit()

        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        bottomAppBar?.visibility = View.VISIBLE

        val kamera = requireActivity().findViewById<FloatingActionButton>(R.id.kamera)
        kamera?.visibility = View.VISIBLE

        val bottomNavigationView = requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.selectedItemId = R.id.home

        (activity as MainActivity).onLoginSuccess()
    }

    private fun showWelcomeDialog(firstName: String) {
        val alertDialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Selamat Datang")
            .setMessage("Selamat datang, $firstName!")
            .setCancelable(false)
            .create()

        alertDialog.show()

        android.os.Handler().postDelayed({
            alertDialog.dismiss()
        }, 1800)
    }
}