package com.example.aday2dream.view.prompt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.navigation.NavController
import com.example.aday2dream.viewmodel.prompt.PromptViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.res.colorResource
import com.example.aday2dream.R
import com.example.aday2dream.view.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewPromptScreen(
    postId: Long,
    promptViewModel: PromptViewModel,
    navController: NavController
) {
    val prompts by promptViewModel.prompts.observeAsState(emptyList())

    LaunchedEffect(postId) {
        promptViewModel.getPromptsByPostId(postId)
    }

    Scaffold(
        topBar = {
            TopBar(
                label = "Prompts",
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (prompts?.isEmpty() == true) {
            Box(
                modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.pink_secondary)),
                contentAlignment = Alignment.Center,

            ) {
                Text("No prompts available")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues).background(color = colorResource(R.color.pink_secondary)), ) {
                items(prompts ?: emptyList()) { prompt ->
                    com.example.aday2dream.view.PromptItem(prompt) { promptId ->
                        navController.navigate("prompt/$promptId")
                    }
                }

            }
        }
    }
}
