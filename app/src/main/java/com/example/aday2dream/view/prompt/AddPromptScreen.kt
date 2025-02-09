package com.example.aday2dream.view.prompt

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.aday2dream.R
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.TextField
import com.example.aday2dream.view.TopBar
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun AddPromptScreen(
    promptViewModel: PromptViewModel,
    postViewModel: PostViewModel,
    accountViewModel: AccountViewModel,
    onPromptAdded: () -> Unit,
    postId: Long
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hyperlinks by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(LocalDateTime.now()) }
    var endDate by remember { mutableStateOf<LocalDateTime?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val profile by accountViewModel.profile.observeAsState()
    val post by postViewModel.post.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(profile) {
        accountViewModel.fetchProfile()
    }

    val openStartDatePicker = remember { mutableStateOf(false) }
    val openEndDatePicker = remember { mutableStateOf(false) }

    if (openStartDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                startDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                openStartDatePicker.value = false
            },
            startDate.year, startDate.monthValue - 1, startDate.dayOfMonth
        ).show()
    }

    if (openEndDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                endDate = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
                openEndDatePicker.value = false
            },
            endDate?.year ?: startDate.year,
            (endDate?.monthValue ?: startDate.monthValue) - 1,
            endDate?.dayOfMonth ?: startDate.dayOfMonth
        ).show()
    }


    Scaffold(
        contentColor = colorResource(R.color.pink_secondary),
        containerColor = colorResource(R.color.pink_secondary),
        topBar = {
            TopBar(
                label = "Post Details",
                navigationIcon = {
                    androidx.compose.material3.IconButton(onClick = { }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add New Prompt",
                color = colorResource(R.color.purple_main),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp)
            )

            TextField(
                value = title,
                onValueChange = { title = it },
                label = "Title",
                modifier = Modifier.width(300.dp)
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = "Description" ,
                modifier = Modifier.width(300.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = hyperlinks,
                onValueChange = { hyperlinks = it },
                label = "Hyperlinks (comma-separated)",
                modifier = Modifier.width(300.dp)
            )


            Button(text = "Pick Start Date",
                modifier = Modifier.width(200.dp),
                onClick = { openStartDatePicker.value = true }
            )
            Text(text = "Start Date: $startDate", color = colorResource(R.color.purple_main))


            Button(text = "Pick End Date",
                modifier = Modifier.width(200.dp),
                onClick = { openEndDatePicker.value = true }
            )

            Text(text = "End Date: ${endDate ?: "Not Set"}", color = colorResource(R.color.purple_main))

            Log.d("Prompt Screen", "accountId: ${profile?.accountId}")
            Log.d("Prompt Screen", "postId: $postId")

            Button(
                text = "Submit",
                onClick = {
                    isLoading = true
                    profile?.accountId?.let { accountId ->
                        post?.postId?.let { postId ->
                            endDate?.let { endDate ->

                                val dateFormatter =
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")


                                val formattedStartDate = startDate.format(dateFormatter)
                                val formattedEndDate = endDate?.format(dateFormatter)

                                promptViewModel.createPrompt(
                                    postId = postId,
                                    buyerId = accountId,
                                    promptTitle = title,
                                    promptDescription = description,
                                    hyperlinks = hyperlinks,
                                    startDate = formattedStartDate,
                                    endDate = formattedEndDate,
                                    onSuccess = {
                                        isLoading = false
                                        onPromptAdded()
                                    }
                                ) {}
                            }
                        }
                    }
                },
                enabled = title.isNotEmpty() && description.isNotEmpty() && !isLoading,
                modifier = Modifier.width(200.dp)
            )
        }
    }
}
