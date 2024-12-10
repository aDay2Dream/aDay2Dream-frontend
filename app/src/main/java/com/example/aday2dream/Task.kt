package com.example.aday2dream

import android.annotation.SuppressLint
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class Task(
    var post: Post,
    var startDate: LocalDateTime,
    var deadlineDays: Int,
    var deadlineWeeks: Int,
    var deadlineMonths: Int,
    var timeLeft: Int = 0
) {
    @SuppressLint("NewApi")
    fun calculateTimeLeft() {

        val deadlineDate = startDate
            .plusDays(deadlineDays.toLong())
            .plusWeeks(deadlineWeeks.toLong())
            .plusMonths(deadlineMonths.toLong())


        val now = LocalDateTime.now()
        timeLeft = if (now.isBefore(deadlineDate)) {
            ChronoUnit.DAYS.between(now, deadlineDate).toInt()
        } else {
            0 
        }
    }

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}
