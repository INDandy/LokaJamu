package com.dicoding.mybottomnavtest.FragmentLogin.ui.login


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mybottomnavtest.ForgetPassword.ForgetFragment
import com.dicoding.mybottomnavtest.FragmentRegister.RegisterFragment
import com.dicoding.mybottomnavtest.MainActivity
import com.dicoding.mybottomnavtest.R
import com.dicoding.mybottomnavtest.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val loginEditText = binding.emailEditText
        val passwordEditText = binding.passwordEditText
        val loginButton = binding.loginButton
        val forgotPassword = view.findViewById<TextView>(R.id.forgotPassword)
        val register = view.findViewById<TextView>(R.id.createAccountLink)
        val loadingProgressBar = binding.loading

        loginButton.isEnabled = false


        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginFormState ->
            if (loginFormState == null) {
                return@Observer
            }

            loginButton.isEnabled = loginFormState.isDataValid

            loginFormState.loginError?.let {
                loginEditText.error = getString(it)
            }
            loginFormState.passwordError?.let {
                passwordEditText.error = getString(it)
            }

            if (loginFormState.isDataValid) {
                loginButton.resources.getString(R.string.register)
            } else {
                loginButton.resources.getString(R.string.register)
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                loginResult.error?.let {
                    showLoginFailed(it)
                }
                loginResult.success?.let {
                    updateUiWithUser(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        loginEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                loginEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

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

        register.setOnClickListener {
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
    }


    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        (activity as? MainActivity)?.onLoginSuccess()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}