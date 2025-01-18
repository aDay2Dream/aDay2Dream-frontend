package com.example.aday2dream.view

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aday2dream.R
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.viewmodel.AudioFileViewModel
import com.example.aday2dream.viewmodel.PostViewModel
import java.math.BigDecimal


@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(
    onPostCreated: () -> Unit,
    audioFileViewModel: AudioFileViewModel,
    postViewModel: PostViewModel,
    accountViewModel: AccountViewModel
) {
    val profile by accountViewModel.profile.observeAsState()
    var postTitle by remember { mutableStateOf("") }
    var postDescription by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var audioTitle by remember { mutableStateOf("") }
    var audioDuration by remember { mutableStateOf("") }
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Launcher for picking audio files
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            fileUri = uri
            message = "File selected: ${uri.lastPathSegment}"
        } else {
            message = "No file selected"
        }
    }

    LaunchedEffect(Unit) {
        accountViewModel.fetchProfile()
    }





    Scaffold(
        snackbarHost = { SnackbarHost(hostState = SnackbarHostState()) },
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(R.drawable.logo_name),
                        contentDescription = stringResource(R.string.logo_content_description)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(R.color.pink_secondary)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input fields for post details
            OutlinedTextField(
                value = postTitle,
                onValueChange = { postTitle = it },
                label = { Text("Post Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = postDescription,
                onValueChange = { postDescription = it },
                label = { Text("Post Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { launcher.launch("audio/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Audio File")
            }

            // Audio file details (if selected)
            if (fileUri != null) {
                OutlinedTextField(
                    value = audioTitle,
                    onValueChange = { audioTitle = it },
                    label = { Text("Audio Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = audioDuration,
                    onValueChange = { audioDuration = it },
                    label = { Text("Audio Duration (e.g., 3:45)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    audioFileViewModel.uploadAudio(
                        context = context,
                        fileUri = fileUri!!,
                        title = audioTitle,
                        duration = audioDuration,
                        onSuccess = { uploadedAudio ->
                            Log.d("onSuccess uploadAudio", uploadedAudio.toString())
                            postViewModel.createPost(
                                postTitle = postTitle,
                                postDescription = postDescription,
                                price = price.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                                audioFile = uploadedAudio,
                                account = profile!!,
                                backgroundImage = "",
                                onSuccess = {
                                    message = "Post created successfully!"
                                    onPostCreated()
                                },
                                onError = { err ->
                                    message = err
                                }
                            )
                        },
                        onError = { err ->
                            message = err
                        }
                    )
        },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Post")
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("success", true)) Color.Green else Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

