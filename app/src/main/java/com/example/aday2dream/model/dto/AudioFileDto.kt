package com.example.aday2dream.model.dto

data class AudioFileDto(
    val audiofileId: Long,
    val uri: String,
    val title: String,
    val duration: Int,
    val uploadedAt: String
)

