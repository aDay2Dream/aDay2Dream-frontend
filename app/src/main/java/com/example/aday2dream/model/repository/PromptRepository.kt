package com.example.aday2dream.model.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.PromptDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response

class PromptRepository(
    private val apiService: ApiService
) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun getPromptById(promptId: Long) : Response<PromptDto>{
        return apiService.getPromptById("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}", promptId)
    }

    suspend fun createPrompt(prompt: PromptDto) : Response<PromptDto> {
        Log.d("Prompt Repository", "Entered Prompt Repository")
        val response = apiService.createPrompt("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}", prompt)
        Log.d("Prompt Repository", response.body().toString())
        return response
    }


}