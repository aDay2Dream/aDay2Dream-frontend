package com.example.aday2dream

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime


data class Account(
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var password: String = "",
    var posts: List<Post>? = null,
    var activePosts: List<Post>? = null,
    var createdAt: LocalDateTime? = null,
    var profilePicture: Painter? = null
) {
        fun isNotEmpty(): Boolean {
            return username.isNotEmpty() && password.isNotEmpty()
        }

}