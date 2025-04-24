package com.example.aday2dream.viewmodel.prompt

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aday2dream.model.dto.PromptDto
import com.example.aday2dream.model.repository.PromptRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PromptViewModel(private val promptRepository: PromptRepository) : ViewModel() {

    private val _prompt = MutableLiveData<PromptDto?>()
    val prompt: LiveData<PromptDto?> get() = _prompt

    private val _prompts = MutableLiveData<List<PromptDto>?>()
    val prompts: MutableLiveData<List<PromptDto>?> get() = _prompts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    @SuppressLint("NewApi")
    fun createPrompt(
        postId: Long,
        buyerId: Long,
        promptTitle: String,
        promptDescription: String,
        hyperlinks: String,
        startDate: String,
        endDate: String?,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prompt = PromptDto(
                    promptTitle = promptTitle,
                    promptDescription = promptDescription,
                    promptLinks = hyperlinks,
                    postId = postId,
                    buyerId = buyerId,
                    startDate = startDate,
                    endDate = endDate,
                )
                val jsonData = Gson().toJson(prompt)
                Log.d("Prompt ViewModel", jsonData)
                val response = promptRepository.createPrompt(prompt)
                Log.d("Prompt ViewModel", response.toString())
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess("Prompt created Successfully!")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Failed to create prompt: ${response.message() ?: "Unknown error"}")
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error: ${e.localizedMessage}")
                }
            }
        }
    }

    fun getPrompt(promptId: Long) {
        viewModelScope.launch {
             try {
                val result = promptRepository.getPromptById(promptId)
                _prompt.value = result.body()
            } catch (e: Exception) {
                _error.value = "Error fetching prompt"
            }
        }
    }

    fun getPromptsByAccountId(accountId: Long) {
        viewModelScope.launch {
            try {
                val result = promptRepository.getPromptsByAccountId(accountId)
                _prompts.postValue(result.body())
            } catch (e: Exception) {
                _error.value = "Error fetching prompt"
            }
        }
    }

    fun getPromptsByPostId(postId: Long) {
        viewModelScope.launch {
            try {
                val result = promptRepository.getPromptsByPostId(postId)
                _prompts.value = result.body()
            } catch (e: Exception) {
                _error.value = "Error fetching prompt"
            }
        }
    }
}