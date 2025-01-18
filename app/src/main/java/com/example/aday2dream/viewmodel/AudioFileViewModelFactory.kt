package com.example.aday2dream.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aday2dream.model.repository.AudioFileRepository
import com.example.aday2dream.model.repository.PostRepository

class AudioFileViewModelFactory(private val audiofileRepository: AudioFileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AudioFileViewModel::class.java)) {
            AudioFileViewModel(audiofileRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}