package com.example.aday2dream.model.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.AccountDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.Response

class AccountRepository(private val apiService: ApiService) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun getProfile(): AccountDto? {
        val response = apiService.getProfile(authHeader = "Bearer ${
            dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()
        }")
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw Exception("Failed to fetch profile: ${response.message()}")
        }
    }

    suspend fun deleteAccount(accountId: Long) {
        val response = apiService.deleteAccount(authHeader = "Bearer ${
            dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()
        }", accountId)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete account: ${response.message()}")
        }
    }

    suspend fun updateProfile(accountId: Long, account: AccountDto): AccountDto? {

        val response = apiService.updateAccount(
            authHeader = "Bearer ${
            dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()
        }", accountId, account)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Failed to update account: ${response.message()}")
        }
    }


    suspend fun logoutAccount(): String? {
        val response = apiService.logoutAccount(authHeader = "Bearer ${
            dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()
        }")
        return response.body()
    }

    suspend fun getAccountById(accountId: Long): AccountDto? {
        return try {
            val response = apiService.getAccountById(authHeader = "Bearer ${
                dataStore.data
                    .map { it[AUTH_TOKEN] }
                    .first()
            }", accountId)

            return response.body()
        } catch (e: Exception) {
            Log.e("AccountRepository", "Error fetching account: ${e.message}")
            null
        }
    }
}
