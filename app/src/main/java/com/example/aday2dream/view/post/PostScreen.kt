package com.example.aday2dream.view.post


import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aday2dream.viewmodel.post.PostViewModel
import androidx.compose.material3.IconButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.aday2dream.model.dto.AudioFileDto
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    postId: Long,
    postViewModel: PostViewModel,
    accountViewModel: AccountViewModel,
    audioFileViewModel: AudioFileViewModel,
    navController: NavController
) {
    val postState by postViewModel.post.collectAsState(initial = null)
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val account by accountViewModel.account.observeAsState()
    var audioFile by remember { mutableStateOf<AudioFileDto?>(null) }


    LaunchedEffect(postId) {
        postViewModel.getPostById(postId)
    }

    LaunchedEffect(postState) {
        postState?.let { post ->
            accountViewModel.getAccountById(post.accountId)
            audioFile = audioFileViewModel.getAudioFileById(post.audiofileId)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Post Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (postState != null && account != null && audioFile != null) {
            val post = postState!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "By: ${account!!.username}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Price: $${post.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if (audioFile != null) {
                    Text(
                        text = "Audio: ${audioFile!!.title}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = {
                            playAudio(context, audioFile!!.uri)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Play Audio")
                    }
                }
                Button(
                    onClick = {
                        navController.navigate("addPrompt/${post.postId}")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create Prompt")
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

private fun playAudio(context: Context, audioUri: String) {
    try {
        val mediaPlayer = MediaPlayer().apply {
            setDataSource(context, Uri.parse(audioUri))
            prepare()
            start()
        }
    } catch (e: Exception) {
        Log.e("PostScreen", "Error playing audio: ${e.localizedMessage}")
    }
}