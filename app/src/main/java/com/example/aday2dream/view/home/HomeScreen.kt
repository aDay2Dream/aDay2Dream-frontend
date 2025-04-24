package com.example.aday2dream.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.aday2dream.R
import com.example.aday2dream.view.HomeBottomBar
import com.example.aday2dream.view.PostItem
import com.example.aday2dream.view.TopBar
import com.example.aday2dream.viewmodel.post.PostViewModel

@Composable
fun HomeScreen(
    navigateToPost: (postId: Long) -> Unit,
    navigateToAddPost: () -> Unit,
    navigateToProfile: () -> Unit,
    viewModel: PostViewModel
) {
    val posts by viewModel.posts.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(
            painter = painterResource(R.drawable.logo_name)
        ) },
        bottomBar = { HomeBottomBar(navigateToAddPost, navigateToProfile) },
        containerColor = colorResource(R.color.pink_secondary)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (posts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No posts yet. Be the first to share!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(posts) { post ->
                        PostItem(post, navigateToPost)
                    }
                }
            }
        }
    }
}





