package com.example.chefmate.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefmate.api.ApiClient
import com.example.chefmate.api.ApiConstant
import com.example.chefmate.helper.DataStoreHelper
import com.example.chefmate.model.LoginRequest
import com.example.chefmate.model.LoginResponse
import com.example.chefmate.model.RegisterRequest
import com.example.chefmate.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val api = ApiClient.createService(ApiConstant.MAIN_URL)

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    fun isLoggedIn(context: Context) {
        viewModelScope.launch {
            _isLoggedIn.value = DataStoreHelper.isLoggedIn(context)
            if (_isLoggedIn.value) {
                getUserData(context)
            }
        }
    }

    fun saveLoginState(context: Context, userData: UserData) {
        viewModelScope.launch {
            DataStoreHelper.saveLoginState(
                context = context,
                isLoggedIn = true,
                userId = userData.userId,
                username = userData.fullName,
                email = userData.email,
                phoneNumber = userData.phone,
                followCount = userData.followCount,
                recipeCount = userData.recipeCount,
                createdAt = userData.createdAt
            )
            isLoggedIn(context)
        }
    }

    fun getUserData(context: Context) {
        viewModelScope.launch {
            _user.value = DataStoreHelper.getUserData(context)
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            DataStoreHelper.clearLoginState(context)
            _isLoggedIn.value = false
            _user.value = null
        }
    }

    suspend fun login(phone: String, password: String): LoginResponse {
        return api.login(LoginRequest(phone, password))
    }

    suspend fun requester(fullName: String, phone: String, email: String, password: String): LoginResponse {
        return api.register(RegisterRequest(fullName, phone, email, password))
    }

}