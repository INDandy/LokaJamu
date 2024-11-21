package com.dicoding.mybottomnavtest.FragmentRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dicoding.mybottomnavtest.FragmentLogin.ui.login.LoginFragment
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingProgressBar = binding.loading
        val signin = binding.signInLink

        // Observing LiveData from ViewModel
        registerViewModel.registerState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RegisterState.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Register successful!", Toast.LENGTH_SHORT).show()
                    val transaction = parentFragmentManager.beginTransaction()
                        transaction.setCustomAnimations(
                            R.anim.enter_from_right,
                            R.anim.exit_to_left,
                            R.anim.enter_from_left,
                            R.anim.exit_to_right
                        )
                        .replace(R.id.frame_layout, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                }
                is RegisterState.Error -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                }
                is RegisterState.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                }
            }
        })

        // Setting up button click listener
        binding.registerButton.setOnClickListener {
            val username = binding.firstnameEditText.text.toString()
            val fullname = binding.lastnameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            registerViewModel.register(username,fullname, email, password)
        }
        signin.setOnClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}