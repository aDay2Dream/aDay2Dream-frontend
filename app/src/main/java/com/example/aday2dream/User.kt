package com.example.aday2dream

data class User(
    var email: String?,
    var firstName: String,
    var lastName: String,
    var credentials: Credentials,
    var posts: List<Post>
)