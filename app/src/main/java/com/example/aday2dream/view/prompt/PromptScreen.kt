package com.example.aday2dream.view.prompt

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aday2dream.viewmodel.prompt.PromptViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.mail.MailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptScreen(promptId: Long, promptViewModel: PromptViewModel,
                 accountViewModel: AccountViewModel,
                 onNavigateBack: () -> Unit,
                 mailViewModel: MailViewModel,
                 context: Context
) {
    val prompt by promptViewModel.prompt.observeAsState()
    val account by accountViewModel.account.observeAsState()
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(promptId) {
        promptViewModel.getPrompt(promptId)
    }

    LaunchedEffect(prompt?.buyerId) {
        prompt?.let { accountViewModel.getAccountById(it.buyerId) }
    }
    Log.d("Prompt Screen", "buyerId: ${prompt?.buyerId}")

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prompt Details") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                prompt?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = "Title: ${it.promptTitle}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Description: ${it.promptDescription}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Start Date: ${it.startDate}")
                        Text(text = "End Date: ${it.endDate}")
                    }
                } ?: Text("Prompt not found", modifier = Modifier.align(Alignment.Center))
                Column() {
                    Button(
                        onClick = { launcher.launch("audio/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Select Audio File")
                    }

                    Log.d("Prompt Screen", "fileUri: $fileUri")
                    Log.d("Prompt Screen", message)
                    Log.d("Prompt Screen", "Account: ${account.toString()}")
                    Button(
                        onClick = {
                                account?.let {
                                    mailViewModel.sendEmail(
                                        context = context,
                                        recipientEmail = it.email,
                                        subject = "Your music is ready!",
                                        body = "Go get your music!",
                                        fileUri = fileUri!!,
                                        onSuccess = {
                                            onNavigateBack()
                                        },
                                        onError = { error ->
                                            Log.d("Mail Sending", error)
                                        })
                                }
                        }
                    ) {
                        Text("Send Audiofile!")
                    }
                }
            }
        }
    )
}
