package com.example.aday2dream.view.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aday2dream.R
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.TextField
import com.example.aday2dream.view.TopBar
import com.example.aday2dream.viewmodel.post.PostViewModel
import java.math.BigDecimal

@Composable
fun EditPostScreen(
    postId: Long,
    postViewModel: PostViewModel,
    onNavigateBack: () -> Unit
) {
    val post by postViewModel.post.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(post) {
        postViewModel.getPostById(postId)
    }

    if (post == null) {
        Text("Loading...")
        return
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.pink_secondary)),
        containerColor = colorResource(R.color.pink_secondary),
        topBar =
        {
            TopBar(
                label = "Edit Post",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colorResource(R.color.purple_main)
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update your post information:",
                style = TextStyle(fontSize = 18.sp),
                color = colorResource(R.color.purple_main)
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
                label = "Description",
                modifier = Modifier.width(300.dp)
            )

            TextField(
                value = price,
                onValueChange = { price = it },
                label = "Price",
                modifier = Modifier.width(300.dp)
            )


            Button(
                text = "Save Changes",
                onClick = {
                    val updatedPost = post!!.copy(
                        title = title,
                        description = description,
                        price = price.toBigDecimalOrNull() ?: BigDecimal.ZERO
                    )
                    postViewModel.editPost(
                        postId = postId,
                        updatedPost = updatedPost,
                        onSuccess = onNavigateBack,
                        onError = { errorMessage = it }
                    )
                },
                modifier = Modifier.width(200.dp)
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = Color.Red)
            }
        }
    }
}