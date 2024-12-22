package com.example.aday2dream

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private var loginMessage: String? = null
    private var registrationMessage: String? = null

    fun login(username: String, password: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.login(AccountLoginDto(username, password))
                loginMessage = response["message"]
                onResult(loginMessage)

            } catch (e: Exception) {
                onResult("Login failed: ${e.message}")
            }
        }
    }

    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val accountDto = Account(
                    username = username,
                    email = email,
                    firstName = firstName,
                    lastName = lastName
                )
                val response = RetrofitClient.api.register(password = password, accountDto = accountDto)

                val message = response["message"] ?: "Registration successful"
                onResult(message)
            } catch (e: Exception) {
                onResult("Registration failed: ${e.message}")
            }
        }
    }
}

