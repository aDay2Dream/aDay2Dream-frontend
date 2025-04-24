package com.example.aday2dream.view.login

import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aday2dream.model.dto.AccountLoginDto
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.R
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.RegisterTextButton
import com.example.aday2dream.view.TextField
import com.example.aday2dream.viewmodel.account.LoginState


@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginSuccess: () -> Unit, viewModel: AccountViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    var errorMessage by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    var account by remember { mutableStateOf(AccountLoginDto()) }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
        containerColor = colorResource(R.color.pink_secondary)
        ){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Image(modifier = Modifier.size(150.dp, 150.dp), painter = painterResource(R.drawable.logo), contentDescription = stringResource(id = R.string.logo_content_description))
            Spacer(Modifier.height(20.dp))
            TextField(
                value = account.username,
                onValueChange = { data -> account = account.copy(username = data) },
                modifier = Modifier.width(300.dp),
                label = "Username"
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                value = account.password,
                onValueChange = { data -> account = account.copy(password = data) },
                modifier = Modifier.width(300.dp),
                visualTransformation = PasswordVisualTransformation(),
                label = "Password"
            )
            Spacer(Modifier.height(20.dp))
            Button(
                text = stringResource(R.string.login_button_text),
                modifier = Modifier.size(100.dp, 50.dp), onClick = {
                viewModel.login(
                    accountLoginDto = AccountLoginDto(username = account.username, password = account.password)
                ) { error, token ->
                    if (error != null) {
                        errorMessage = error
                        Log.d("Login Button", errorMessage)
                    } else if (token != null) {
                        viewModel.saveAuthToken(token)
                        Log.d("Login Button", "Token Saved")
                        onLoginSuccess()
                    }
                }
            })
            Spacer(Modifier.height(5.dp))
            RegisterTextButton(onNavigateToRegister = { onNavigateToRegister() } )

            when (loginState) {
                is LoginState.Loading -> CircularProgressIndicator()
                is LoginState.Success -> {
                    Text("Login Successful!")
                    LaunchedEffect(Unit) { onLoginSuccess() }
                }
                is LoginState.Error -> Text((loginState as LoginState.Error).message, color = Color.Red)
                else -> {}
            }

        }
    }
}

