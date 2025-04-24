package com.example.aday2dream.model.dto

data class AudioFileDto(
    val audiofileId: Long,
    val title: String,
    val uri: String,
    val duration: Int,
    val uploadedAt: String
)

