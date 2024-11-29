package com.dicoding.mybottomnavtest.FragmentRegister

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.mybottomnavtest.FragmentLogin.LoginFragment
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.ViewModelFactory
import com.dicoding.mybottomnavtest.customviewmodel.emailview
import com.dicoding.mybottomnavtest.customviewmodel.lastnameview
import com.dicoding.mybottomnavtest.customviewmodel.nameview
import com.dicoding.mybottomnavtest.customviewmodel.passwordview
import com.dicoding.mybottomnavtest.databinding.FragmentRegisterBinding
import com.dicoding.mybottomnavtest.preference.ResultValue
import com.dicoding.mybottomnavtest.viewmodel.MainViewModel

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var myButton: Button
    private lateinit var FirstNameEditText: nameview
    private lateinit var LastNameEditText: lastnameview
    private lateinit var emailEditText: emailview
    private lateinit var passwordEditText: passwordview
    private lateinit var ConfirmPasswordEditText: passwordview
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirstNameEditText = binding.firstnameEditText
        LastNameEditText = binding.lastnameEditText
        emailEditText = binding.emailEditText
        passwordEditText = binding.passwordEditText
        ConfirmPasswordEditText = binding.RePasswordEditText
        myButton = binding.registerButton

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtons()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtons()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        binding.signInLink.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.enter_from_left,
                R.anim.exit_to_right,
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
            transaction.replace(R.id.frame_layout, LoginFragment())
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
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun register() {
        val firstName = binding.firstnameEditText.text.toString().trim()
        val lastName = binding.lastnameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.RePasswordEditText.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("All fields must be filled out")
            return
        }

        viewModel.register(firstName, lastName, email, password, confirmPassword)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultValue.Loading -> showLoading(true)
                    is ResultValue.Success<*> -> {
                        showToast("Registration Successful").toString()
                        showLoading(false)
                        navigateToLogin()
                    }
                    is ResultValue.Error -> {
                        showToast(result.error)
                        showLoading(false)
                    }
                }
            }
    }

    private fun setButtons() {
        val emailResult = emailEditText.text.toString().isNotEmpty() && emailEditText.error == null
        val passwordResult = passwordEditText.text.toString().isNotEmpty() && passwordEditText.error == null

        if (emailResult && passwordResult) {
            myButton.isEnabled = true
            myButton.setOnClickListener { register() }
        } else {
            myButton.isEnabled = false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun navigateToLogin() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
        transaction.replace(R.id.frame_layout, LoginFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


