package com.example.aday2dream.model.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.protobuf.Api
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.AudioFileDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class AudioFileRepository(private val api: ApiService) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun uploadAudio(
        file: MultipartBody.Part,
        title: RequestBody,
        duration: RequestBody
    ): Response<AudioFileDto> {
        return api.uploadAudio("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}",file, title, duration)
    }

    suspend fun getAudioFileById(audioFileId: Long): AudioFileDto? {
        return try {
            api.getAudioFileById(authHeader = "Bearer ${dataStore.data
                .map { it[AUTH_TOKEN] }
                .first()}", audioFileId)
        } catch (e: Exception) {
            Log.e("AudioFileRepository", "Error fetching audio file: ${e.message}")
            null
        }
    }

}

