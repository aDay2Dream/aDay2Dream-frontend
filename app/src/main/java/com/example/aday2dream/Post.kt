package com.example.aday2dream

data class Post (
    var title: String,
    var description: String,
    var price: Int,
    var isActive: Boolean?,
    var media: List<AudioFile>
)