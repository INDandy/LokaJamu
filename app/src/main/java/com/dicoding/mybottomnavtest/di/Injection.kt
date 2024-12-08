package com.dicoding.mybottomnavtest.di

import android.content.Context
import com.dicoding.mybottomnavtest.api.ApiClient
import com.dicoding.mybottomnavtest.preference.UserPreference
import com.dicoding.mybottomnavtest.preference.dataStore
import com.dicoding.mybottomnavtest.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.UserApiService()
        return UserRepository.getInstance(apiService, pref)
    }
}