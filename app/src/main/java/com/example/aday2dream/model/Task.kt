package com.example.aday2dream.model

import android.annotation.SuppressLint
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Task(
    var post: Post,
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var timeLeft: Int = 0
) {
    @SuppressLint("NewApi")
    fun calculateTimeLeft() {
        val now = LocalDateTime.now()
        timeLeft = if (now.isBefore(endDate)) {
            ChronoUnit.DAYS.between(now, endDate).toInt()
        } else {
            0
        }
    }

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}
