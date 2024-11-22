package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.mybottomnavtest.FragmentEvents.CameraFragment
import com.dicoding.mybottomnavtest.FragmentEvents.FavoriteFragment
import com.dicoding.mybottomnavtest.FragmentEvents.HomeFragment
import com.dicoding.mybottomnavtest.FragmentEvents.NewsFragment
import com.dicoding.mybottomnavtest.FragmentEvents.SettingsFragment
import com.dicoding.mybottomnavtest.FragmentLogin.ui.login.LoginFragment
import com.dicoding.mybottomnavtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isUserLoggedIn = false // Simulasi status login pengguna

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Awal: sembunyikan bottom bar
        binding.bottomAppBar.visibility = View.GONE
        binding.kamera.visibility = View.GONE

        if (!isUserLoggedIn) {
            // Load LoginFragment pertama kali
            loadFragment(LoginFragment(), showBottomBar = false)
        } else {
            // Jika sudah login, tampilkan HomeFragment dan bottom bar
            binding.bottomNavigationView.visibility = View.VISIBLE
            binding.bottomNavigationView.selectedItemId = R.id.home
            loadFragment(HomeFragment())
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            resetIconSizes()

            val itemView = binding.bottomNavigationView.findViewById<View>(item.itemId)

            itemView?.animate()?.scaleX(1.3f)?.scaleY(1.3f)?.setDuration(170)?.start()

            binding.bottomNavigationView.setBackgroundResource(android.R.color.transparent)

            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.news -> {
                    loadFragment(NewsFragment())
                    true
                }
                R.id.favorite -> {
                    loadFragment(FavoriteFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        binding.kamera.setOnClickListener {
            loadFragment(CameraFragment())
        }
    }

    private fun resetIconSizes() {
        val menu = binding.bottomNavigationView.menu
        for (i in 0 until menu.size()) {
            val menuItem = binding.bottomNavigationView.findViewById<View>(menu.getItem(i).itemId)
            menuItem?.animate()?.scaleX(1f)?.scaleY(1f)?.setDuration(200)?.start()
        }
    }

    private fun loadFragment(fragment: Fragment, showBottomBar: Boolean = true) {
        if (showBottomBar) {
            binding.bottomNavigationView.visibility = View.VISIBLE
        } else {
            binding.bottomNavigationView.visibility = View.GONE
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    fun onLoginSuccess() {
        isUserLoggedIn = true

        binding.bottomAppBar.visibility = View.VISIBLE
        binding.kamera.visibility = View.VISIBLE

        loadFragment(HomeFragment())

        binding.bottomNavigationView.selectedItemId = R.id.home
    }
    }
