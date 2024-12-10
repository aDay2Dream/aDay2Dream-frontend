package com.example.aday2dream

import com.google.gson.Gson
import java.time.LocalDateTime

data class User(
    var email: String?,
    var firstName: String,
    var lastName: String,
    var credentials: Credentials,
    var posts: List<Post>,
    var activePosts: List<Post>,
    var createdAt: LocalDateTime
){
    fun toJson() : String {
        val gson = Gson()
        return gson.toJson(this)
    }
}