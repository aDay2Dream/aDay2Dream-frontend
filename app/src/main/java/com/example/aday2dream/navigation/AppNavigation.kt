package com.example.aday2dream.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aday2dream.view.*
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.viewmodel.AudioFileViewModel
import com.example.aday2dream.viewmodel.PostViewModel

@Composable
fun AppNavigation(navController: NavHostController, accountViewModel: AccountViewModel, postViewModel: PostViewModel, audioFileViewModel: AudioFileViewModel) {
    NavHost(
        navController = navController,
        startDestination = com.example.aday2dream.navigation.Screen.Login.route
    ) {
        // Login Screen
        composable(com.example.aday2dream.navigation.Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(com.example.aday2dream.navigation.Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(com.example.aday2dream.navigation.Screen.Home.route)
                },
                viewModel = accountViewModel

            )
        }

        // Register Screen
        composable(com.example.aday2dream.navigation.Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(com.example.aday2dream.navigation.Screen.Login.route) {
                        popUpTo(com.example.aday2dream.navigation.Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = accountViewModel
            )
        }


        // Home Screen
        composable(com.example.aday2dream.navigation.Screen.Home.route) {
            HomeScreen(
                navigateToPost = { postId ->
                    navController.navigate(
                        com.example.aday2dream.navigation.Screen.Post.createRoute(
                            postId.toString()
                        )
                    )
                },
                navigateToAddPost = { navController.navigate(com.example.aday2dream.navigation.Screen.AddPost.route) },
                navigateToProfile = { navController.navigate(com.example.aday2dream.navigation.Screen.Profile.route) },
                viewModel = postViewModel
            )
        }

        composable(com.example.aday2dream.navigation.Screen.AddPost.route) {
            AddPostScreen(
                onPostCreated = { navController.popBackStack() },
                audioFileViewModel = audioFileViewModel,
                accountViewModel = accountViewModel,
                postViewModel = postViewModel
            )
        }
        composable(com.example.aday2dream.navigation.Screen.Post.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            if (postId != null) {
                PostScreen(postId = postId, postViewModel, navController)
            }
    }
        composable(com.example.aday2dream.navigation.Screen.Profile.route) {
            AccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateLogin = { navController.navigate(com.example.aday2dream.navigation.Screen.Login.route) },
                onEditInfo = {navController.navigate(com.example.aday2dream.navigation.Screen.EditAccount.route)},
                accountViewModel = accountViewModel,
                postViewModel = postViewModel
            )
        }
        composable(com.example.aday2dream.navigation.Screen.EditAccount.route){
            EditAccountScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateLogin = { navController.navigate(com.example.aday2dream.navigation.Screen.Login.route){
                popUpTo(0)
            } },
                accountViewModel = accountViewModel
            )
        }
    }

}



    /*
        // Add Post Screen


        // Profile Screen


        // Add Prompt Screen
        composable(Screen.AddPrompt.route) {
            AddPromptScreen(
                onPromptAdded = { navController.popBackStack() }
            )
        }

        // Prompt Details Screen
        composable(Screen.Prompt.route) {
            PromptScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
*/
