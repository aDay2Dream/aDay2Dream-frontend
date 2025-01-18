package com.example.aday2dream.view

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
import androidx.navigation.NavController
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    onNavigateLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    accountViewModel: AccountViewModel,
    postViewModel: PostViewModel
) {

    val profile by accountViewModel.profile.observeAsState()
    val posts by postViewModel.posts.observeAsState(emptyList())
    val error by accountViewModel.error.observeAsState()


    LaunchedEffect(Unit) {
        accountViewModel.fetchProfile()
        profile?.let { postViewModel.fetchPostsByAccount(it.accountId) } // Fetch posts made by this account
    }

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
                    AccountDetails(
                        account = account,
                        posts = posts,
                        onEditInfo = {

                        },
                        onDeleteAccount = {
                            accountViewModel.deleteAccount(
                                onSuccess = { onNavigateLogin() },
                                onError = { /* Handle delete error */ }
                            )
                        },
                        onLogoutAccount = {
                            accountViewModel.logoutAccount(
                                onSuccess = { onNavigateLogin() },
                                onError = {}
                            )
                        }
                    )
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
    account: AccountDto,
    posts: List<PostDto>,
    onEditInfo: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLogoutAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile Information
        Text(text = "Profile Information", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Username: ${account.username}")
        Text(text = "Email: ${account.email}")
        Text(text = "First Name: ${account.firstName}")
        Text(text = "Last Name: ${account.lastName}")

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onEditInfo) {
                Text("Edit Info")
            }
            Button(onClick = onDeleteAccount, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Delete Account")
            }
            Button(onClick = onLogoutAccount, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
            Text("Log Out")
        }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Posts Section
        Text(text = "Your Posts", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (posts.isNotEmpty()) {
            LazyColumn {
                items(posts) { post ->
                    PostItem(post = post, onClick = { /* Navigate to Post Details */ })
                }
            }
        } else {
            Text(text = "No posts available.", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun PostItem(post: PostDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
