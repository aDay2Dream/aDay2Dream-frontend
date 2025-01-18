package com.example.aday2dream.model.api

import com.example.aday2dream.model.dto.AccountLoginDto
import com.example.aday2dream.model.Account
import com.example.aday2dream.model.AudioFile
import com.example.aday2dream.model.Post
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.model.dto.PostDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.http.*

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)

data class RegisterResponse(
        val username: String,
        val password: String,
        val email: String,
        val firstName: String,
        val lastName: String
)

data class ProfileResponse(
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val profilePicture: String?,
        val description: String?,
        val links: String?
)

data class PostResponse(
        val id: Long,
        val title: String,
        val description: String,
        val price: Double,
        val publisher: Account,
        val audiofile: AudioFile
)



interface ApiService {
        @POST("/accounts/login")
        suspend fun login(@Body request: AccountLoginDto): Response<LoginResponse>

        @POST("/accounts/register")
        suspend fun register(@Body request: Account, @Query("password") password: String): Response<RegisterResponse>

        @GET("/accounts/profile")
        suspend fun getProfile(@Header("Authorization") authHeader: String): Response<AccountDto>

        @GET("/posts")
        suspend fun getPosts(@Header("Authorization") authHeader: String): Response<List<PostDto>>

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
        suspend fun getPostById(@Header("Authorization") authHeader: String, @Path("id") postId: String): Post

        @DELETE("/profile")
        suspend fun deleteAccount(@Header("Authorization") authHeader: String): Response<Unit>

        @POST("/accounts/logout")
        suspend fun logoutAccount(@Header("Authorization") authHeader: String): Response<Unit>

        @GET("/posts/account/{accountId}")
        suspend fun getPostsByAccount(@Header("Authorization") authHeader: String, @Path("accountId") accountId: Long): Response<List<PostDto>>
}

