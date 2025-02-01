package com.example.aday2dream.model.api

import com.example.aday2dream.model.dto.AccountLoginDto
import com.example.aday2dream.model.Account
import com.example.aday2dream.model.Post
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.model.dto.PromptDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.http.*

data class LoginResponse(val token: String)

data class RegisterResponse(
        val username: String,
        val password: String,
        val email: String,
        val firstName: String,
        val lastName: String
)

interface ApiService {
        @POST("/accounts/login")
        suspend fun login(@Body request: AccountLoginDto): Response<LoginResponse>

        @POST("/accounts/register")
        suspend fun register(@Body request: Account): Response<RegisterResponse>

        @GET("/accounts/profile")
        suspend fun getProfile(@Header("Authorization") authHeader: String): Response<AccountDto>

        @GET("/posts")
        suspend fun getPosts(@Header("Authorization") authHeader: String): Response<List<Post>>

        @Multipart
        @POST("audiofiles/upload")
        suspend fun uploadAudio(
                @Header("Authorization") authHeader: String,
                @Part file: MultipartBody.Part,
                @Part("title") title: RequestBody,
                @Part("duration") duration: RequestBody
        ): Response<AudioFileDto>

        @POST("/posts")
        suspend fun createPost(@Header("Authorization") authHeader: String, @Body post: Post): Response<Void>

        @GET("posts/{id}")
        suspend fun getPostById(@Header("Authorization") authHeader: String, @Path("id") postId: Long): Post

        @DELETE("/profile")
        suspend fun deleteAccount(@Header("Authorization") authHeader: String): Response<Unit>

        @POST("/accounts/logout")
        suspend fun logoutAccount(@Header("Authorization") authHeader: String): Response<Unit>

        @GET("/posts/account/{accountId}")
        suspend fun getPostsByAccount(@Header("Authorization") authHeader: String, @Path("accountId") accountId: Long): Response<List<Post>>

        @PUT("accounts/{id}")
        suspend fun updateAccount(@Header("Authorization") authHeader: String,  @Path("id") accountId: Long, @Body account: Account, @Query("password") password: String): Response<AccountDto>

        @PUT("posts/{id}")
        suspend fun updatePost(@Header("Authorization") authHeader: String, @Path("id") postId: Long, @Body updatedPost: Post): Response<Post>

        @DELETE("/posts/{id}")
        suspend fun deletePost(@Header("Authorization") authHeader: String, @Path("id") postId: Long): Response<Unit>

        @GET("/prompts/{id}")
        suspend fun getPromptById(@Header("Authorization") authHeader: String, @Path("id") promptId: Long): Response<PromptDto>

        @POST("/prompts")
        suspend fun createPrompt(@Header("Authorization") authHeader: String, @Body prompt: PromptDto): Response<PromptDto>
}

