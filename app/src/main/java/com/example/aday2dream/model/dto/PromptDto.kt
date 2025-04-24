package com.example.aday2dream.model.dto
import java.time.LocalDateTime

data class PromptDto(
        val promptId: Long? = null,
        val postId: Long,
        val buyerId: Long,
        val promptTitle: String,
        val promptDescription: String,
        val promptLinks: String? = null,
        val startDate: String,
        val endDate: String? = null,
)
