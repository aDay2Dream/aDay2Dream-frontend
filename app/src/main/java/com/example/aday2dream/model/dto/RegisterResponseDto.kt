package com.example.aday2dream.model.dto

data class RegisterResponseDto(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
)