package com.example.aday2dream.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AddPost : Screen("addPost")
    object Profile : Screen("profile")
    object EditAccount: Screen("editAccount")

    object Post : Screen("post/{postId}") {
        fun createRoute(postId: Long) = "post/$postId"
    }

    object EditPost : Screen("editPost/{postId}")
    {
        fun createRoute(postId: Long) = "editPost/$postId"
    }

    object AddPrompt: Screen("addPrompt/{postId}") {
        fun createRoute(postId: Long) = "addPrompt/$postId"
    }

    object Prompt: Screen("prompt/{promptId}")
    {
        fun createRoute(promptId: Long) = "prompt/$promptId"
    }

    object ViewPrompt: Screen("viewPrompt/{postId}")
    {
        fun createRoute(postId: Long) = "viewPrompt/$postId"
    }

}
