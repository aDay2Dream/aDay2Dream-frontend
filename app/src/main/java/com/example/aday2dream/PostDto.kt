package com.example.aday2dream

data class PostDto(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val backgroundImage: String?,
    val account: Account
)

