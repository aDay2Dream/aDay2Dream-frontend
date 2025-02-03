package com.example.aday2dream.viewmodel.mail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aday2dream.model.repository.MailRepository

class MailViewModelFactory(private val mailRepository: MailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MailViewModel::class.java)) {
            MailViewModel(mailRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}