package com.example.aday2dream.model.dto

data class AccountDto (
    val accountId: Long? = null,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profilePicture: String? = null,
    val description: String? = null,
    val links: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
    )