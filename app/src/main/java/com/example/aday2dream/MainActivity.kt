package com.example.aday2dream

import android.annotation.SuppressLint
import androidx.navigation.compose.NavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.aday2dream.presentation.ui.LoginPage
import com.example.aday2dream.ui.theme.ADay2DreamTheme


class MainActivity : ComponentActivity() {
    public val viewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADay2DreamTheme {
                ADay2DreamApp(viewModel = viewModel)
            }
                }
            }
        }

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun ADay2DreamApp(viewModel: AccountViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController, viewModel = viewModel) }
        composable("home") { HomePage(navController, viewModel = viewModel)}
        composable("post") { PostPage(navController) }
        composable("register") { RegisterPage(navController, viewModel = viewModel) }
        composable("account") { AccountPage(navController) }
            composable("addpost") { AddPostPage(navController) }
        }
    }


