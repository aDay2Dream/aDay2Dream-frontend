package com.example.aday2dream.model.dto

import java.math.BigDecimal


data class PostDto(
    val postId: Long,
    val accountId: Long,
    val audiofileId: Long,
    val title: String,
    val description: String,
    val backgroundImage: String? = null,
    val hyperlinks: String? = null,
    val price: BigDecimal
)

