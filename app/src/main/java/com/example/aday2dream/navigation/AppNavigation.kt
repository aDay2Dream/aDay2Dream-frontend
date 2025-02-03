package com.example.aday2dream.navigation

import AddPromptScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aday2dream.view.account.AccountScreen
import com.example.aday2dream.view.account.EditAccountScreen
import com.example.aday2dream.view.home.HomeScreen
import com.example.aday2dream.view.login.LoginScreen
import com.example.aday2dream.view.login.RegisterScreen
import com.example.aday2dream.view.post.AddPostScreen
import com.example.aday2dream.view.post.EditPostScreen
import com.example.aday2dream.view.post.PostScreen
import com.example.aday2dream.view.prompt.PromptScreen
import com.example.aday2dream.view.prompt.ViewPromptScreen
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModel
import com.example.aday2dream.viewmodel.mail.MailViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModel

@Composable
fun AppNavigation(navController: NavHostController, accountViewModel: AccountViewModel, postViewModel: PostViewModel, audioFileViewModel: AudioFileViewModel, promptViewModel: PromptViewModel, mailViewModel: MailViewModel) {
    NavHost(
        navController = navController,
        startDestination = com.example.aday2dream.navigation.Screen.Login.route
    ) {

        composable(com.example.aday2dream.navigation.Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route)
                },
                viewModel = accountViewModel

            )
        }

        composable(com.example.aday2dream.navigation.Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                },
                viewModel = accountViewModel
            )
        }

        composable(com.example.aday2dream.navigation.Screen.Home.route) {
            HomeScreen(
                navigateToPost = { postId ->
                    navController.navigate(
                        Screen.Post.createRoute(
                            postId
                        )
                    )
                },
                navigateToAddPost = { navController.navigate(Screen.AddPost.route) },
                navigateToProfile = { navController.navigate(Screen.Profile.route) },
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
                        navController = navController,
                        accountViewModel = accountViewModel,
                        audioFileViewModel = audioFileViewModel
                    )
                } else {
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
                },
                onViewPrompt = {
                        promptId ->
                    navController.navigate(
                        com.example.aday2dream.navigation.Screen.ViewPrompt.createRoute(
                            promptId
                        )
                    )
                },
                promptViewModel = promptViewModel
            )
        }
        composable(com.example.aday2dream.navigation.Screen.EditAccount.route) {
            EditAccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateLogin = {
                    navController.navigate(Screen.Login.route) {
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
                Log.e("Navigation", "Post ID is null!")
            }
        }

        composable(route = com.example.aday2dream.navigation.Screen.AddPrompt.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })
            ) {
                backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId")
            if (postId != null) {
                AddPromptScreen(
                    onPromptAdded = { navController.popBackStack() },
                    promptViewModel = promptViewModel,
                    accountViewModel = accountViewModel,
                    postViewModel = postViewModel,
                    postId = postId
                )
            }
        }
        composable(route = com.example.aday2dream.navigation.Screen.ViewPrompt.route,
            arguments = listOf(navArgument("postId") { type = NavType.LongType })) { backStackEntry ->
            val postId = backStackEntry.arguments?.getLong("postId")

            if (postId != null) {
                ViewPromptScreen(
                    postId = postId,
                    promptViewModel = promptViewModel,
                    navController = navController
                )
            }
        }

        composable(route = com.example.aday2dream.navigation.Screen.Prompt.route,
                arguments = listOf(navArgument("promptId") { type = NavType.LongType })) { backStackEntry ->
                    val promptId = backStackEntry.arguments?.getLong("promptId")

            if (promptId != null) {
                PromptScreen(
                    onNavigateBack = { navController.popBackStack() },
                    promptId = promptId,
                    promptViewModel = promptViewModel,
                    accountViewModel = accountViewModel,
                    mailViewModel = mailViewModel,
                    context = LocalContext.current
                )
            }
        }
    }
}






