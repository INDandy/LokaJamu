package com.dicoding.mybottomnavtest.repository

import androidx.lifecycle.liveData
import com.dicoding.mybottomnavtest.Response.LoginRequest
import com.dicoding.mybottomnavtest.Response.RegisterRequest
import com.dicoding.mybottomnavtest.UserModel
import com.dicoding.mybottomnavtest.api.ApiService
import com.dicoding.mybottomnavtest.preference.ResultValue
import com.dicoding.mybottomnavtest.preference.UserPreference
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor (private val apiService: ApiService, private val preference: UserPreference){

    fun register(firstName: String, lastName: String, email: String, password: String, confirmPassword: String) = liveData {
        emit(ResultValue.Loading)
        val request = RegisterRequest(firstName, lastName, email, password, confirmPassword)
        try {
            val response = apiService.register(request)
            emit(ResultValue.Success(response))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
            emit(ResultValue.Error(errorMessage))
        }
    }


    fun login(email: String, password: String) = liveData {
        emit(ResultValue.Loading)

        try {
            val loginRequest = LoginRequest(email, password)
            val loginResponse = apiService.login(loginRequest)

            emit(ResultValue.Success(loginResponse))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
            emit(ResultValue.Error(errorMessage))
        } catch (e: Exception) {
            emit(ResultValue.Error(e.message ?: "An error occurred"))
        }
    }

    fun getSession(): Flow<UserModel> {
        return preference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        preference.saveSession(user)
    }

    suspend fun logout() {
        preference.logout()
        instance = null
    }

    companion object {
        private const val TAG = "MainViewModel"
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, pref: UserPreference) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref)
            }.also { instance = it }
    }
}