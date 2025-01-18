package com.example.aday2dream.model.dto


data class PostDto(
    val postId: Long,
    val title: String,
    val description: String,
    val price: String,
    val backgroundImage: String?,
    val accountId: Long,
    val audiofileId: Any?
)

