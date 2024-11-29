package com.dicoding.mybottomnavtest.FragmentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dicoding.mybottomnavtest.MainActivity

import com.dicoding.mybottomnavtest.R

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Mengakses tombol logout dari layout
        logoutButton = view.findViewById(R.id.logoutButton)

        // Menetapkan OnClickListener untuk tombol logout
        logoutButton.setOnClickListener {
            // Panggil fungsi logout() dari MainActivity
            val activity = requireActivity() as MainActivity
            activity.logout() // Memanggil fungsi logout di MainActivity
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}
