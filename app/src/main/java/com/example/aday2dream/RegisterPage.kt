package com.example.aday2dream

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegisterPage(navController: NavController) {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row()
            {
                TextFieldFirstName()
                TextFieldLastName()
            }
            TextFieldEmail()
            TextFieldUsername()
            TextFieldPassword()
            Button(onClick = {
                navController.navigate("home")
            }) {
                Text("Sign Up");
            }
        }
    }
}

@Composable
fun TextFieldFirstName()
{
    var firstName by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)).width(142.dp),
        value = firstName,
        onValueChange = { firstName = it },
        label = { Text("First Name") }
    )
    Spacer(Modifier.width(10.dp))
}

@Composable
fun TextFieldEmail()
{
    var email by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.width(10.dp))
    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        value = email,
        onValueChange = { email = it },
        label = { Text("E-mail") }
    )
}


@Composable
fun TextFieldLastName()
{
    var lastName by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)).width(142.dp),
        value = lastName,
        onValueChange = { lastName = it },
        label = { Text("Last Name") }
    )
}


