package com.example.aday2dream.model

import android.annotation.SuppressLint
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.google.gson.Gson
import java.math.BigDecimal
import java.time.LocalDateTime

@SuppressLint("NewApi")
data class Post (
    val postId: Long? = null,
    var account: AccountDto,
    var audiofile: AudioFileDto,
    var title: String = "",
    var description: String = "",
    var price: BigDecimal?,
    var backgroundImage: String = "",
){
    fun isNotEmpty() : Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

    fun toJson() : String {
        val gson = Gson()
        return gson.toJson(this)
    }
}