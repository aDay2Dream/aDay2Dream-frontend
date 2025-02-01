package com.example.aday2dream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aday2dream.model.repository.PostRepository
import com.example.aday2dream.model.repository.PromptRepository

class PromptViewModelFactory(private val promptRepository: PromptRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PromptViewModel::class.java)) {
            PromptViewModel(promptRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}