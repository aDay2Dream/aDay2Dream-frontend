package com.example.aday2dream.model.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.Post
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.PostDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response

class PostRepository(private val api: ApiService) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun createPost(post: Post): Response<Void> {
        return api.createPost("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}",post)
    }

    suspend fun getPostById(postId: String): Post {
        return api.getPostById("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}",postId)
    }

    suspend fun getPostsByAccountId(accountId: Long): List<PostDto> {
        val response = api.getPostsByAccount(
            authHeader = "Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}", accountId)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch posts: ${response.message()}")
        }
    }
}

