package com.dicoding.mybottomnavtest.ForgetPassword

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.mybottomnavtest.R
import com.google.android.material.textfield.TextInputLayout

class ForgetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forget, container, false)

        val ivBack = view.findViewById<View>(R.id.ivBack)
        val etEmail = view.findViewById<View>(R.id.etEmail)
        val nextbutton = view.findViewById<View>(R.id.nextbutton)
        val emailEditTextLayout = view.findViewById<TextInputLayout>(R.id.emailEditTextLayout)

        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        nextbutton.setOnClickListener{
            val emailEditText = emailEditTextLayout.editText?.text.toString()
            if (emailEditText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailEditText).matches()) {
                Toast.makeText(requireContext(), "Email submitted: $emailEditText", Toast.LENGTH_SHORT).show()
                // Add logic to handle email submission here
            } else {
                Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show()
            }
        }
return view
    }
}