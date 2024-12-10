package com.example.aday2dream

import android.annotation.SuppressLint
import com.google.gson.Gson
import java.io.File
import java.time.LocalDateTime

@SuppressLint("NewApi")
data class Post (
    var title: String = "",
    var description: String = "",
    var price: Int = 0,
    var isActive: Boolean = true,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var tasks: Int = 0,
    var starred: Boolean = false
){
    fun isNotEmpty() : Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

    fun toJson() : String {
        val gson = Gson()
        return gson.toJson(this)
    }
}