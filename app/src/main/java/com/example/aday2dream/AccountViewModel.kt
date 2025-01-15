package com.example.aday2dream

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aday2dream.data.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AccountViewModel : ViewModel() {
    private var loginResponse: String? = null

    private var registerResponse: String? = null

    private val _registrationState = MutableLiveData<String?>()
    val registrationState: LiveData<String?> get() = _registrationState

    private val dataStore = App.appContext.dataStore // Assuming DataStore is set up

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey("auth_token")] = token
            }
            println("Auth Token saved")
        }
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("auth_token")]
        }
    }

    fun login(accountLoginDto: AccountLoginDto, onResult: (String?, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.login(accountLoginDto)

                if (response.isSuccessful) {
                    val body = response.body()
                    val token = body?.token

                    if (!token.isNullOrEmpty()) {
                        println("Login successful")
                        withContext(Dispatchers.Main){
                            onResult(null, token)
                        }
                         // Login successful, return token
                    } else {
                        withContext(Dispatchers.Main) {
                            onResult("Token not found in response", null)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult("Login failed: ${response.code()} ${response.message()}", null)
                    }

                }
            } catch (e: Exception) {
                onResult("Error: ${e.localizedMessage}", null)
            }
        }
    }


    fun register(
        username: String,
        password: String,
        email: String,
        firstName: String,
        lastName: String,
        onError: (String?) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                val accountDto = Account(
                    username = username,
                    email = email,
                    firstName = firstName,
                    lastName = lastName
                )

                // Pass password as a query parameter
                val response = RetrofitClient.api.register(accountDto, password)
                Log.d("Registration", response.toString())
                if (response.isSuccessful) {
                    registerResponse = "Registration successful"
                    _registrationState.postValue(registerResponse)
                } else {
                    val errorMessage = "Registration Failed: ${response.message()}"
                    _registrationState.postValue(errorMessage)
                    onError(errorMessage)
                }
            } catch (e: HttpException) {
                val errorMessage = "Error: ${e.message()}"
                _registrationState.postValue(errorMessage)
                onError(errorMessage)
            } catch (e: Exception) {
                val errorMessage = "Error: ${e.localizedMessage}"
                _registrationState.postValue(errorMessage)
                onError(errorMessage)
            }
        }

    }
}


