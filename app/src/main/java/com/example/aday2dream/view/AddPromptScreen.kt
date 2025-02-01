import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.viewmodel.PostViewModel
import com.example.aday2dream.viewmodel.PromptViewModel
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Add New Prompt")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hyperlinks,
            onValueChange = { hyperlinks = it },
            label = { Text("Hyperlinks (comma-separated)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { openStartDatePicker.value = true }) {
            Text("Pick Start Date")
        }
        Text(text = "Start Date: $startDate")

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { openEndDatePicker.value = true }) {
            Text("Pick End Date")
        }
        Text(text = "End Date: ${endDate ?: "Not Set"}")

        Spacer(modifier = Modifier.height(16.dp))
        Log.d("Prompt Screen", "accountId: ${profile?.accountId}")
        Log.d("Prompt Screen", "postId: $postId")

        Button(
            onClick = {
                isLoading = true
                profile?.accountId?.let { accountId ->
                    post?.postId?.let { postId ->
                        endDate?.let { endDate ->

                            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")


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
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Submit")
            }
        }
    }
}
