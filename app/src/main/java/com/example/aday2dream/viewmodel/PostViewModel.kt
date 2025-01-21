package com.example.aday2dream.viewmodel

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aday2dream.App
import com.example.aday2dream.dataStore
import com.example.aday2dream.model.Post
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.model.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal


class PostViewModel(private val postRepository: PostRepository) : ViewModel() {
    private val _posts = MutableLiveData<List<Post>>()
    val posts: MutableLiveData<List<Post>> get() = _posts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val dataStore = App.appContext.dataStore
    val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> get() = _post




    fun fetchPosts() {
        viewModelScope.launch {
            try {
                Log.d("Post View Model", "Entered try statement")
                val response = postRepository.fetchPosts()
                Log.d("Post View Model", response.toString())
                if (response.isSuccessful) {
                    Log.d("Post View Model", response.body().toString())
                    _posts.postValue(response.body())

                } else {
                    _error.postValue("Error fetching posts: ${response.message()}")
                    Log.d("Post View Model", "Error fetching posts: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.localizedMessage}")
                Log.d("Post View Model", "Error fetching posts: ${e.localizedMessage}")
            }
        }
    }

    fun createPost(
        postTitle: String,
        backgroundImage: String?,
        postDescription: String,
        price: BigDecimal,
        audioFile: AudioFileDto,
        account: AccountDto,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val post = backgroundImage?.let {
                    Post(
                        title = postTitle,
                        description = postDescription,
                        price = price,
                        audiofile = audioFile,
                        backgroundImage = it,
                        account = account
                    )
                }
                Log.d("Post View Model", post.toString())
                val response = post?.let { postRepository.createPost(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main)
                        {
                            onSuccess("Post created Successfully!")
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onError("Failed to create post: ${response.message()}")
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error: ${e.localizedMessage}")
                }
            }
        }
    }
    fun getPostById(postId: Long) {
        viewModelScope.launch {
            try {
                val fetchedPost = postRepository.getPostById(postId)
                _post.value = fetchedPost
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error fetching post: ${e.localizedMessage}")
            }
        }
    }

    fun fetchPostsByAccount(
        accountId: Long
    ) {
        viewModelScope.launch {
            try {
                val accountPosts = postRepository.getPostsByAccountId(accountId)
                _posts.postValue(accountPosts)
            } catch (e: Exception) {
                _error.postValue(e.localizedMessage)
            }
        }
    }

        fun editPost(
            postId: Long,
            updatedPost: Post,
            onSuccess: () -> Unit,
            onError: (String) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    val response = postRepository.updatePost(postId, updatedPost)
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Failed to update post: ${response.message()}")
                    }
                } catch (e: Exception) {
                    onError("Error occurred: ${e.localizedMessage}")
                }
            }
        }

    fun deletePost(
        postId: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = postRepository.deletePost(postId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Failed to update post: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error occurred: ${e.localizedMessage}")
            }
        }
    }


    }


