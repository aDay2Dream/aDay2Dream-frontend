package com.example.aday2dream.model.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.AccountDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AccountRepository(private val apiService: ApiService) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun getProfile(): AccountDto? {
            val response = apiService.getProfile(authHeader = "Bearer ${dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()}")
            return if (response.isSuccessful) {
                response.body()
            } else {
                throw Exception("Failed to fetch profile: ${response.message()}")
            }
        }
    }
