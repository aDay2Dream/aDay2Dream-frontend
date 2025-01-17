package com.example.aday2dream.model

import android.net.Uri

data class AudioFile (
    val id: Long,
    val uri: String,
    val title: String,
    val duration: Int,
)