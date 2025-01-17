package com.example.aday2dream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aday2dream.model.repository.AccountRepository
import com.example.aday2dream.model.repository.PostRepository

class PostViewModelFactory(private val postRepository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            PostViewModel(postRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}