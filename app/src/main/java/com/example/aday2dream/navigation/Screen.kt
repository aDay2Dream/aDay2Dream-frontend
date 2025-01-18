package com.example.aday2dream.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AddPost : Screen("addPost")
    object Profile : Screen("profile")
    object AddPrompt : Screen("addPrompt")
    object Prompt : Screen("prompt")

    object Post : Screen("post/{postId}") {
        fun createRoute(postId: String) = "post/$postId"
    }
}
