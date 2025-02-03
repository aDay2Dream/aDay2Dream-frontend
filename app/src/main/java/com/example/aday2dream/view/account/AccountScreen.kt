package com.example.aday2dream.view.account

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.livedata.observeAsState
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.model.dto.PromptDto
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onNavigateLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    onEditInfo: () -> Unit,
    onEditPost: (postId: Long) -> Unit,
    onViewPrompt: (promptId: Long) -> Unit,
    accountViewModel: AccountViewModel,
    postViewModel: PostViewModel,
    promptViewModel: PromptViewModel
) {

    val profile by accountViewModel.profile.observeAsState()
    val posts by postViewModel.posts.observeAsState(emptyList())
    val prompts by promptViewModel.prompts.observeAsState(emptyList())
    val error by accountViewModel.error.observeAsState()

    LaunchedEffect(Unit) {
        accountViewModel.fetchProfile()
        profile?.let {
            it.accountId?.let { accountId ->
                postViewModel.fetchPostsByAccount(accountId)
                promptViewModel.getPromptsByAccountId(accountId)
            }
        }
    }
    Log.d("Account Screen", "{${prompts.toString()}}")
    Log.d("Account Screen", posts.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
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
                profile?.let { account ->
                    prompts?.let {
                        AccountDetails(
                            account = account,
                            posts = posts,
                            prompts = it,
                            onEditInfo = onEditInfo,
                            onLogoutAccount = {
                                accountViewModel.logoutAccount(
                                    onSuccess = {
                                        onNavigateLogin()
                                    },
                                    onError = {}
                                )
                            },
                            onEditPost = onEditPost,
                            onViewPrompt = onViewPrompt,
                            postViewModel = postViewModel,
                            promptViewModel = promptViewModel,
                            onNavigateBack = onNavigateBack
                        )
                    }
                } ?: error?.let {
                    Text(
                        text = error.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }
            }
        }
    )
}


@Composable
fun AccountDetails(
    postViewModel: PostViewModel,
    promptViewModel: PromptViewModel,
    account: AccountDto,
    posts: List<PostDto>,
    prompts: List<PromptDto>,
    onEditPost: (postId: Long) -> Unit,
    onViewPrompt: (promptId: Long) -> Unit,
    onEditInfo: () -> Unit,
    onLogoutAccount: () -> Unit,
    onNavigateBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "Profile Information", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Username: ${account.username}")
        Text(text = "Email: ${account.email}")
        Text(text = "First Name: ${account.firstName}")
        Text(text = "Last Name: ${account.lastName}")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onEditInfo) {
                Text("Edit Info")
            }
            Button(onClick = onLogoutAccount, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Log Out")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Your Posts", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (posts.isNotEmpty()) {
            LazyColumn {
                items(posts) { post ->
                    EditPostItem(post = post, onEditPost, postViewModel = postViewModel, onNavigateBack = onNavigateBack, onViewPrompt = onViewPrompt)
                }
            }
        } else {
            Text(text = "No posts available.", modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Your Prompts", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (prompts.isNotEmpty()) {
            LazyColumn {
                items(prompts) { prompt ->
                    PromptItem(prompt = prompt, onViewPrompt)
                }
            }
        } else {
            Text(text = "No prompts available.", modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun EditPostItem(post: PostDto, onEditPost: (postId: Long) -> Unit, onViewPrompt: (postId: Long) -> Unit, postViewModel: PostViewModel, onNavigateBack: () -> Unit) {
    Log.d("Edit Post Item", post.postId.toString())
    val error by remember {mutableStateOf("")}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { post.postId?.let { onViewPrompt(post.postId) } }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
            Button(onClick = { post.postId?.let { onEditPost(post.postId) } }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)){
                Text("Edit Post")
            }
            Button(onClick = { post.postId?.let {
                postViewModel.deletePost(postId = it, onSuccess = {
                    Log.d("Delete Post", "Post Deleted")
                    onNavigateBack()
                }, onError = {})
            } }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)){
                Text("Delete Post")
            }
        }
    }
}

@Composable
fun PromptItem(prompt: PromptDto, onViewPrompt: (promptId: Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { prompt.promptId?.let { onViewPrompt(it) } }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = prompt.promptTitle, style = MaterialTheme.typography.titleMedium)
            Text(text = prompt.promptDescription, style = MaterialTheme.typography.bodyMedium)
            Button(onClick = { prompt.promptId?.let { onViewPrompt(it) } }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text("View Prompt")
            }
        }
    }
}

