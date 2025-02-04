package com.example.aday2dream.model.repository

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.AudioFileDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class MailRepository(private val api: ApiService){
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun sendMail(
        to: RequestBody,
        subject: RequestBody,
        text: RequestBody,
        file: MultipartBody.Part
    ): Response<String> {
        Log.d("Mail Repository", "Sending to endpoint /email/send")
        return api.sendMail("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}", to, subject, text, file)
    }
}