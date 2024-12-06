package com.dicoding.mybottomnavtest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.mybottomnavtest.FragmentEvents.CameraFragment
import com.dicoding.mybottomnavtest.FragmentEvents.FavoriteFragment
import com.dicoding.mybottomnavtest.FragmentEvents.HomeFragment
import com.dicoding.mybottomnavtest.FragmentEvents.NewsFragment
import com.dicoding.mybottomnavtest.FragmentEvents.SettingsFragment
import com.dicoding.mybottomnavtest.FragmentLogin.LoginFragment
import com.dicoding.mybottomnavtest.databinding.ActivityMainBinding
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(applicationContext.dataStore)
            userPreference.getSession().collect { user ->
                if (user.isLoggedIn) {
                    binding.bottomNavigationView.setBackgroundResource(android.R.color.transparent)
                    loadFragment(HomeFragment())
                } else {
                    loadFragment(LoginFragment(), showBottomBar = true)
                }
            }
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
        binding.bottomNavigationView.visibility = if (showBottomBar) View.VISIBLE else View.GONE
        binding.kamera.visibility = if (showBottomBar) View.VISIBLE else View.GONE

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    private fun showBottomAppBarAndNavigation() {

    }

    private fun hideBottomAppBarAndNavigation() {
    }

    fun onLoginSuccess() {
        loadFragment(NewsFragment())
    }
    fun logout() {
        lifecycleScope.launch {
            val userPreference = UserPreference.getInstance(applicationContext.dataStore)
            userPreference.logout()
            hideBottomAppBarAndNavigation()
            loadFragment(LoginFragment(), showBottomBar = false)
        }
    }
}

