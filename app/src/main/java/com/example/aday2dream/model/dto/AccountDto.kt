package com.example.aday2dream.model.dto


data class AccountDto (
    var accountId: Long,
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var password: String = ""
    )