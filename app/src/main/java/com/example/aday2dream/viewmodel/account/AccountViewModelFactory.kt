package com.example.aday2dream.viewmodel.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aday2dream.model.repository.AccountRepository

class AccountViewModelFactory(private val accountRepository: AccountRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            AccountViewModel(accountRepository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}