package com.example.aday2dream.view.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aday2dream.viewmodel.account.AccountViewModel
import com.example.aday2dream.R
import com.example.aday2dream.model.dto.AccountDto
import com.example.aday2dream.view.TextField
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.TopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit, onRegisterSuccess: () -> Unit, viewModel: AccountViewModel) {
    var account by remember { mutableStateOf(AccountDto()) }
    var isLoading by remember { mutableStateOf(false) }
    var registrationMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var errorMessage = remember { mutableStateOf("") }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
        containerColor = colorResource(R.color.pink_secondary),
        topBar = {
            TopBar(
                painter = painterResource(R.drawable.logo_name)
            )
        }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row()
            {
                TextField(
                    value = account.firstName,
                    onValueChange = { data -> account = account.copy(firstName = data) },
                    modifier = Modifier.width(150.dp),
                    label = "First Name"
                )
                Spacer(Modifier.width(10.dp))
                TextField(
                    value = account.lastName,
                    onValueChange = { data -> account = account.copy(lastName = data) },
                    modifier = Modifier.width(150.dp),
                    label = "Last Name"

                )
            }
            Spacer(Modifier.height(10.dp))
            TextField(
                value = account.email,
                onValueChange = { data -> account = account.copy(email = data) },
                modifier = Modifier.width(310.dp),
                label = "E-mail"
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                value = account.username,
                onValueChange = { data -> account = account.copy(username = data) },
                modifier = Modifier.width(310.dp),
                label = "Username"
            )
            Spacer(Modifier.height(10.dp))
            TextField(
                value = account.password,
                onValueChange = {data -> account = account.copy(password = data)},
                modifier = Modifier.width(310.dp),
                visualTransformation = PasswordVisualTransformation(),
                label = "Password"
            )
            Spacer(Modifier.height(20.dp))
            Button(
                text = stringResource(R.string.register_button_text),
                modifier = Modifier.width(150.dp),
                onClick = {
                    viewModel.register(
                        username = account.username,
                        password = account.password,
                        email = account.email,
                        firstName = account.firstName,
                        lastName = account.lastName
                    ) { error ->
                        error?.let { Log.e("Registration", it) }
                    }
                    onRegisterSuccess()
                }
            )
        }
    }
}




