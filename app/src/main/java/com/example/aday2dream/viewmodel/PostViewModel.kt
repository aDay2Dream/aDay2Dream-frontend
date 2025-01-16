package com.example.aday2dream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val _posts = MutableLiveData<List<PostDto>>()
    val posts: LiveData<List<PostDto>> get() = _posts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getPosts()
                if (response.isSuccessful) {
                    _posts.postValue(response.body())
                } else {
                    _error.postValue("Error fetching posts: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.localizedMessage}")
            }
        }
    }
}
