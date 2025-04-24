package com.example.aday2dream.navigation

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
import com.example.aday2dream.view.prompt.AddPromptScreen
import com.example.aday2dream.view.prompt.PromptScreen
import com.example.aday2dream.view.prompt.ViewPromptScreen
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.viewmodel.audiofile.AudioFileViewModel
import com.example.aday2dream.viewmodel.mail.MailViewModel
import com.example.aday2dream.viewmodel.post.PostViewModel
import com.example.aday2dream.viewmodel.prompt.PromptViewModel

@Composable
fun AppNavigation(navController: NavHostController,
                  accountViewModel: AccountViewModel,
                  postViewModel: PostViewModel,
                  audioFileViewModel: AudioFileViewModel,
                  promptViewModel: PromptViewModel,
                  mailViewModel: MailViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route)
                },
                viewModel = accountViewModel
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route)
                    }
                },
                viewModel = accountViewModel
            )
        }

        composable(Screen.Home.route) {
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

        composable(Screen.AddPost.route) {
            AddPostScreen(
                onPostCreated = { navController.popBackStack() },
                audioFileViewModel = audioFileViewModel,
                accountViewModel = accountViewModel,
                postViewModel = postViewModel
            )
        }
        composable(route = Screen.Post.route,
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

        composable(Screen.Profile.route) {
            AccountScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateLogin = {  navController.navigate(Screen.Login.route) },
                onEditInfo = { navController.navigate(Screen.EditAccount.route) },
                accountViewModel = accountViewModel,
                postViewModel = postViewModel,
                onEditPost = {
                        postId ->
                    navController.navigate(
                        Screen.EditPost.createRoute(
                            postId
                        )
                    )
                },
                onViewPrompt = {
                        promptId ->
                    navController.navigate(
                        Screen.ViewPrompt.createRoute(
                            promptId
                        )
                    )
                },
                promptViewModel = promptViewModel
            )
        }
        composable(Screen.EditAccount.route) {
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

        composable(route = Screen.EditPost.route,
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

        composable(route = Screen.AddPrompt.route,
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
        composable(route = Screen.ViewPrompt.route,
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

        composable(route = Screen.Prompt.route,
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






