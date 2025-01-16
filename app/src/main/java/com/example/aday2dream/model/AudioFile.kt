package com.example.aday2dream

import android.net.Uri

data class Audio (
    val uri: Uri,
    val displayName: String,
    val id: Int,
    val data: String,
    val duration: Int,
    val title: String
)