package com.example.aday2dream.view.account

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.aday2dream.R
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.model.dto.PromptDto
import com.example.aday2dream.view.EditPostItem
import com.example.aday2dream.view.PromptItem
import com.example.aday2dream.view.TopBar
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModel
import com.example.aday2dream.view.Button
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
    }

    LaunchedEffect(profile) {
        profile?.accountId?.let { accountId ->
            postViewModel.fetchPostsByAccount(accountId)
            promptViewModel.getPromptsByAccountId(accountId)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                label = "Account",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colorResource(R.color.purple_main))
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.pink_secondary))
        ) {
            Log.d("Account Screen", "$profile , $posts , $prompts")
            profile?.let { account ->
                prompts?.let {
                    AccountDetails(
                        account = account,
                        posts = posts,
                        prompts = it,
                        onEditInfo = onEditInfo,
                        onLogoutAccount = {
                            accountViewModel.logoutAccount(
                                onSuccess = { onNavigateLogin() },
                                onError = {}
                            )
                        },
                        onEditPost = onEditPost,
                        onViewPrompt = onViewPrompt,
                        postViewModel = postViewModel
                    )
                }
            } ?: error?.let {
                Text(
                    text = error.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AccountDetails(
    postViewModel: PostViewModel,
    account: AccountDto,
    posts: List<PostDto>,
    prompts: List<PromptDto>,
    onEditPost: (postId: Long) -> Unit,
    onViewPrompt: (promptId: Long) -> Unit,
    onEditInfo: () -> Unit,
    onLogoutAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = colorResource(R.color.pink_secondary),
                contentColor = colorResource(R.color.purple_main),
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Gray
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Profile Information", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "👤 Username: ${account.username}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "📧 Email: ${account.email}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "📌 First Name: ${account.firstName}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "📌 Last Name: ${account.lastName}", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom)
                    {
                        Button(onClick = onEditInfo, text = "Edit Profile", modifier = Modifier.width(150.dp))
                        Spacer(Modifier.width(40.dp))
                        Button(onClick = onLogoutAccount, text = "Log out", modifier = Modifier.width(150.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.pink_secondary)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Your Posts", style = MaterialTheme.typography.headlineSmall, color = colorResource(R.color.purple_main))
                Spacer(modifier = Modifier.height(8.dp))

                if (posts.isNotEmpty()) {
                    LazyColumn {
                        items(posts) { post ->
                            EditPostItem(
                                post = post,
                                onEditPost = onEditPost,
                                postViewModel = postViewModel,
                                onNavigateBack = {},
                                onViewPrompt = onViewPrompt
                            )
                        }
                    }
                } else {
                    Text(text = "No posts available.", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium, color = colorResource(R.color.pink_secondary))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.pink_secondary)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Your Prompts", style = MaterialTheme.typography.headlineSmall, color = colorResource(R.color.pink_secondary))
                Spacer(modifier = Modifier.height(8.dp))

                if (prompts.isNotEmpty()) {
                    Text(text = "Your Prompts", style = MaterialTheme.typography.headlineSmall, color = colorResource(R.color.purple_main))
                    LazyColumn {
                        items(prompts) { prompt ->
                            Log.d("AccountDetails", "$prompt")
                            PromptItem(prompt = prompt, onViewPrompt = onViewPrompt)
                        }
                    }
                } else {
                    Text(text = "No prompts available.", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineMedium, color = colorResource(R.color.pink_secondary))
                }
            }
        }
    }
}
