package com.example.aday2dream.view.post

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aday2dream.R
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.TextField
import com.example.aday2dream.view.TopBar
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
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
            TopBar(
                label = "Add post"
            )
        },
        containerColor = colorResource(R.color.pink_secondary)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = postTitle,
                onValueChange = { postTitle = it },
                label = "Post Title",
                modifier = Modifier.width(300.dp)
            )

            TextField(
                value = postDescription,
                onValueChange = { postDescription = it },
                label = "Post Description",
                modifier = Modifier.width(300.dp)
            )

           TextField(
                value = price,
                onValueChange = { price = it },
                label = "Price",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(300.dp)
            )

            Button(
                text = "Select Audio File",
                onClick = { launcher.launch("audio/*") },
                modifier = Modifier.fillMaxWidth()
            )
            if (fileUri != null) {
               TextField(
                    value = audioTitle,
                    onValueChange = { audioTitle = it },
                    label = "Title",
                    modifier = Modifier.width(300.dp)
                )

                TextField(
                    value = audioDuration,
                    onValueChange = { audioDuration = it },
                    label = "Audio Duration (e.g., 3:45)",
                    modifier = Modifier.width(300.dp)
                )
            }

            Button(
                modifier = Modifier.width(300.dp),
                text = "Submit Post",
                onClick = {
                    audioFileViewModel.uploadAudio(
                        context = context,
                        fileUri = fileUri!!,
                        title = audioTitle,
                        duration = audioDuration,
                        onSuccess = { uploadedAudio ->
                            Log.d("onSuccess uploadAudio", uploadedAudio.toString())
                            profile!!.accountId?.let {
                                postViewModel.createPost(
                                    postTitle = postTitle,
                                    postDescription = postDescription,
                                    price = price.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                                    audiofileId = uploadedAudio.audiofileId,
                                    accountId = it,
                                    backgroundImage = "",
                                    onSuccess = {
                                        message = "Post created successfully!"
                                        onPostCreated()
                                    },
                                    onError = { err ->
                                        message = err
                                    }
                                )
                            }
                        },
                        onError = { err ->
                            message = err
                        }
                    )
        },
            )

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

