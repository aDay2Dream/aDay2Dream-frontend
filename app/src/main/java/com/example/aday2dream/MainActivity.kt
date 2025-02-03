package com.example.aday2dream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.example.aday2dream.model.api.RetrofitClient
import com.example.aday2dream.model.repository.AccountRepository
import com.example.aday2dream.model.repository.AudioFileRepository
import com.example.aday2dream.model.repository.MailRepository
import com.example.aday2dream.model.repository.PostRepository
import com.example.aday2dream.model.repository.PromptRepository
import com.example.aday2dream.navigation.AppNavigation
import com.example.aday2dream.ui.theme.ADay2DreamTheme
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.account.AccountViewModelFactory
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModel
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModelFactory
import com.example.aday2dream.viewmodel.mail.MailViewModel
import com.example.aday2dream.viewmodel.mail.MailViewModelFactory
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.post.PostViewModelFactory
import com.example.aday2dream.viewmodel.prompt.PromptViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModelFactory


class MainActivity : ComponentActivity() {
    lateinit var accountViewModel: AccountViewModel
    lateinit var postViewModel: PostViewModel
    lateinit var audioFileViewModel: AudioFileViewModel
    lateinit var promptViewModel: PromptViewModel
    lateinit var mailViewModel: MailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val apiService = RetrofitClient.api

        val accountRepository = AccountRepository(apiService)
        val postRepository = PostRepository(apiService)
        val audiofileRepository = AudioFileRepository(apiService)
        val promptRepository = PromptRepository(apiService)
        val mailRepository = MailRepository(apiService)

        accountViewModel = ViewModelProvider(this, AccountViewModelFactory(accountRepository))
            .get(AccountViewModel::class.java)
        postViewModel = ViewModelProvider(this, PostViewModelFactory(postRepository))
            .get(PostViewModel::class.java)
        audioFileViewModel = ViewModelProvider(this, AudioFileViewModelFactory(audiofileRepository))
            .get(AudioFileViewModel::class.java)
        promptViewModel = ViewModelProvider(this, PromptViewModelFactory(promptRepository))
            .get(PromptViewModel::class.java)
        mailViewModel = ViewModelProvider(this, MailViewModelFactory(mailRepository))
            .get(MailViewModel::class.java)


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADay2DreamTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController,
                    accountViewModel, postViewModel, audioFileViewModel, promptViewModel, mailViewModel)
            }
        }
    }
}


