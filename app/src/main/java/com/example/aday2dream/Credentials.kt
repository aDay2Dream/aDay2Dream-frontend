package com.example.aday2dream

data class Credentials (
    var username: String = "",
    var password: String = "",
    var remember: Boolean = false
    ) {
        fun isNotEmpty(): Boolean {
            return username.isNotEmpty() && password.isNotEmpty()
        }
}