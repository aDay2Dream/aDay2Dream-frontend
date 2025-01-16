package com.example.aday2dream

import com.example.aday2dream.model.Account
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
        val audiofile: Audiofile
)

data class Account(val username: String)
data class Audiofile(val title: String, val uri: String)

interface ApiService {
        @POST("/accounts/login")
        suspend fun login(@Body request: AccountLoginDto): Response<LoginResponse>

        @POST("/accounts/register")
        suspend fun register(@Body request: Account, @Query("password") password: String): Response<RegisterResponse>

        @GET("/accounts/profile")
        suspend fun getProfile(): Response<ProfileResponse>

        @GET("/posts")
        suspend fun getPosts(): Response<List<PostResponse>>
}

