package com.example.aday2dream.model.api

import com.example.aday2dream.ACCOUNT_BY_ID
import com.example.aday2dream.CREATE_POST
import com.example.aday2dream.CREATE_PROMPT
import com.example.aday2dream.GET_AUDIOFILE
import com.example.aday2dream.GET_POSTS
import com.example.aday2dream.GET_PROFILE
import com.example.aday2dream.LOG_IN
import com.example.aday2dream.LOG_OUT
import com.example.aday2dream.POSTS_BY_ACCOUNT
import com.example.aday2dream.POST_BY_ID
import com.example.aday2dream.PROMPTS_BY_ACCOUNT_ID
import com.example.aday2dream.PROMPTS_BY_POST_ID
import com.example.aday2dream.PROMPT_BY_ID
import com.example.aday2dream.REGISTER
import com.example.aday2dream.SEND_EMAIL
import com.example.aday2dream.UPDATE_POST
import com.example.aday2dream.UPLOAD_AUDIOFILE
import com.example.aday2dream.model.dto.AccountLoginDto
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.model.dto.LoginResponseDto
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.model.dto.PromptDto
import com.example.aday2dream.model.dto.RegisterResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.http.*

interface ApiService {
        @POST(LOG_IN)
        suspend fun login(@Body request: AccountLoginDto): Response<LoginResponseDto>

        @POST(REGISTER)
        suspend fun register(@Body request: AccountDto): Response<RegisterResponseDto>

        @GET(GET_PROFILE)
        suspend fun getProfile(@Header("Authorization") authHeader: String): Response<AccountDto>

        @GET(ACCOUNT_BY_ID)
        suspend fun getAccountById(@Header("Authorization") authHeader: String, @Path("id") accountId: Long): Response<AccountDto>

        @PUT(ACCOUNT_BY_ID)
        suspend fun updateAccount(@Header("Authorization") authHeader: String, @Path("id") accountId: Long, @Body account: AccountDto): Response<AccountDto>

        @DELETE(ACCOUNT_BY_ID)
        suspend fun deleteAccount(@Header("Authorization") authHeader: String, @Path("id") accountId: Long): Response<Unit>

        @POST(LOG_OUT)
        suspend fun logoutAccount(@Header("Authorization") authHeader: String): Response<String>

        @Multipart
        @POST(UPLOAD_AUDIOFILE)
        suspend fun uploadAudio(
                @Header("Authorization") authHeader: String,
                @Part file: MultipartBody.Part,
                @Part("title") title: RequestBody,
                @Part("duration") duration: RequestBody
        ): Response<AudioFileDto>

        @GET(GET_AUDIOFILE)
        suspend fun getAudioFileById(@Header("Authorization") authHeader: String, @Path("id") audioFileId: Long): AudioFileDto

        @POST(CREATE_POST)
        suspend fun createPost(@Header("Authorization") authHeader: String, @Body post: PostDto): Response<Void>

        @GET(GET_POSTS)
        suspend fun getPosts(@Header("Authorization") authHeader: String): Response<List<PostDto>>

        @GET(POST_BY_ID)
        suspend fun getPostById(@Header("Authorization") authHeader: String, @Path("id") postId: Long): PostDto

        @GET(POSTS_BY_ACCOUNT)
        suspend fun getPostsByAccount(@Header("Authorization") authHeader: String, @Path("publisherId") accountId: Long): Response<List<PostDto>>

        @PUT(UPDATE_POST)
        suspend fun updatePost(@Header("Authorization") authHeader: String, @Path("id") postId: Long, @Body updatedPost: PostDto): Response<PostDto>

        @DELETE(POST_BY_ID)
        suspend fun deletePost(@Header("Authorization") authHeader: String, @Path("id") postId: Long): Response<Unit>

        @GET(PROMPT_BY_ID)
        suspend fun getPromptById(@Header("Authorization") authHeader: String, @Path("id") promptId: Long): Response<PromptDto>

        @GET(PROMPTS_BY_ACCOUNT_ID)
        suspend fun getPromptsByAccountId(@Header("Authorization") authHeader: String, @Path("accountId") accountId: Long): Response<List<PromptDto>>

        @POST(CREATE_PROMPT)
        suspend fun createPrompt(@Header("Authorization") authHeader: String, @Body prompt: PromptDto): Response<PromptDto>

        @GET(PROMPTS_BY_POST_ID)
        suspend fun getPromptsByPostId(@Header("Authorization") authHeader: String, @Path("postId") postId: Long): Response<List<PromptDto>>

        @Multipart
        @POST(SEND_EMAIL)
        suspend fun sendMail(
                @Header("Authorization") authHeader: String,
                @Part("to") to: RequestBody,
                @Part("subject") subject: RequestBody,
                @Part("text") text: RequestBody,
                @Part audioFile: MultipartBody.Part
        ): Response<String>
}

