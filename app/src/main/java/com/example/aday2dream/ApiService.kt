package com.example.aday2dream

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

        @POST("accounts/register")
        suspend fun register(@Query("password") password: String, @Body accountDto: Account): Map<String, String>


        @POST("accounts/login")
        suspend fun login(@Body accountLoginDTO: AccountLoginDto): Map<String, String>
}
