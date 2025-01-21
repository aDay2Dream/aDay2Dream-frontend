package com.example.aday2dream.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                            postId
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
        composable(route = com.example.aday2dream.navigation.Screen.Post.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getLong("postId")
                Log.d("Post Nav", "{postId: $postId}")
                if (postId != null) {
                    PostScreen(
                        postId = postId,
                        postViewModel = postViewModel,
                        navController = navController
                    )
                } else {
                    // Handle the case where postId is null
                    Log.e("Navigation", "Post ID is null!")
                }
            }

        composable(com.example.aday2dream.navigation.Screen.Profile.route) {
            AccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateLogin = { navController.navigate(com.example.aday2dream.navigation.Screen.Login.route) },
                onEditInfo = { navController.navigate(com.example.aday2dream.navigation.Screen.EditAccount.route) },
                accountViewModel = accountViewModel,
                postViewModel = postViewModel,
                onEditPost = {
                        postId ->
                    navController.navigate(
                        com.example.aday2dream.navigation.Screen.EditPost.createRoute(
                            postId
                        )
                    )
                }
            )
        }
        composable(com.example.aday2dream.navigation.Screen.EditAccount.route) {
            EditAccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateLogin = {
                    navController.navigate(com.example.aday2dream.navigation.Screen.Login.route) {
                        popUpTo(0)
                    }
                },
                accountViewModel = accountViewModel
            )
        }
        composable(route = com.example.aday2dream.navigation.Screen.EditPost.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId")
            Log.d("Edit Post Nav", "{postId: $postId}")
            if (postId != null) {
                EditPostScreen(
                    postId = postId,
                    postViewModel = postViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            } else {
                // Handle the case where postId is null
                Log.e("Navigation", "Post ID is null!")
            }
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
