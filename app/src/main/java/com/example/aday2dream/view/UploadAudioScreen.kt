package com.example.aday2dream.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aday2dream.viewmodel.AudioFileViewModel

@Composable
fun UploadAudioScreen(viewModel: AudioFileViewModel) {
    val context = LocalContext.current
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var title by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (e.g., 3:45)") }
        )
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "audio/*"
                }
                (context as Activity).startActivityForResult(intent, 1)
            }
        ) {
            Text("Select Audio File")
        }
        fileUri?.let {
            Button(
                onClick = {
                    viewModel.uploadAudio(
                        context = context,
                        fileUri = it,
                        title = title,
                        duration = duration,
                        onSuccess = { msg -> message = msg.toString() },
                        onError = { err -> error = err}
                    )
                }
            ) {
                Text("Upload")
            }
        }
        if (message.isNotEmpty()) {
            Text(text = message, color = Color.Green)
        }
    }
}

