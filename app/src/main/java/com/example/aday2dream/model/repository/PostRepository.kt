package com.example.aday2dream.model.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.dto.PostDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response

class PostRepository(private val api: ApiService) {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    private val dataStore = App.appContext.dataStore

    suspend fun createPost(post: PostDto): Response<Void> {
        return api.createPost("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}", post)
    }

    suspend fun fetchPosts() : Response<List<PostDto>>{
        return api.getPosts(authHeader = "Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}")
    }


    suspend fun getPostById(postId: Long): PostDto {
        return api.getPostById("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}",postId)
    }

    suspend fun updatePost(postId: Long, updatedPost: PostDto) : Response<PostDto> {
        return api.updatePost("Bearer ${dataStore.data
            .map { it[AUTH_TOKEN] }
            .first()}",postId, updatedPost)
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

    suspend fun deletePost(postId: Long) : Response<Unit> {
        return try{
            val response = api.deletePost(
                authHeader = "Bearer ${dataStore.data
                    .map { it[AUTH_TOKEN] }
                    .first()}", postId)
            return response
        } catch (e: Exception) {
            throw Exception("Failed to delete post.")
        }
    }
}

