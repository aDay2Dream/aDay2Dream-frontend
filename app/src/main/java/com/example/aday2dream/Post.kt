package com.example.aday2dream

data class Post (
    var title: String = "",
    var description: String = "",
    var price: Int = 0,
    var isActive: Boolean = true,
){
    fun isNotEmpty() : Boolean{
        return title.isNotEmpty() && description.isNotEmpty()
    }
}