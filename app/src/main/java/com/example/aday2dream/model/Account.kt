package com.example.aday2dream.data

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime


data class Account(
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var password: String = "",
    var profilePicture: Painter? = null
) {

}