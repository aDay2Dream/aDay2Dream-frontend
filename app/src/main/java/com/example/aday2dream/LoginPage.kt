package com.example.aday2dream

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LoginPage(navController: NavController)
{
    var username by remember { mutableStateOf("Hello") }
    Column()
    {
        Text("aDay2Dream")
        TextFieldUsername()
        TextFieldPassword()
        Text(modifier = Modifier.clickable(), text = "Not signed in yet? Register now")

    }
}

@Composable
fun TextFieldUsername() {
    var username by remember { mutableStateOf("Hello") }

    TextField(
        value = username,
        onValueChange = { username = it },
        label = { Text("Label") }
    )
}

@Composable
fun TextFieldPassword() {
    var password by remember { mutableStateOf("Hello") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") }
    )
}