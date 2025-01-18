package com.example.aday2dream

import android.annotation.SuppressLint
import androidx.navigation.compose.NavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.aday2dream.model.api.ApiService
import com.example.aday2dream.model.api.RetrofitClient
import com.example.aday2dream.model.repository.AccountRepository
import com.example.aday2dream.model.repository.AudioFileRepository
import com.example.aday2dream.model.repository.PostRepository
import com.example.aday2dream.navigation.AppNavigation
import com.example.aday2dream.ui.theme.ADay2DreamTheme
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.viewmodel.AccountViewModelFactory
import com.example.aday2dream.viewmodel.AudioFileViewModel
import com.example.aday2dream.viewmodel.AudioFileViewModelFactory
import com.example.aday2dream.viewmodel.PostViewModel
import com.example.aday2dream.viewmodel.PostViewModelFactory


class MainActivity : ComponentActivity() {
    lateinit var accountViewModel: AccountViewModel
    lateinit var postViewModel: PostViewModel
    lateinit var audioFileViewModel: AudioFileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val apiService = RetrofitClient.api

        val accountRepository = AccountRepository(apiService)
        val postRepository = PostRepository(apiService)
        val audiofileRepository = AudioFileRepository(apiService)

        accountViewModel = ViewModelProvider(this, AccountViewModelFactory(accountRepository))
            .get(AccountViewModel::class.java)
        postViewModel = ViewModelProvider(this, PostViewModelFactory(postRepository))
            .get(PostViewModel::class.java)
        audioFileViewModel = ViewModelProvider(this, AudioFileViewModelFactory(audiofileRepository))
            .get(AudioFileViewModel::class.java)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADay2DreamTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController, accountViewModel, postViewModel, audioFileViewModel)
            }
        }
    }
}


