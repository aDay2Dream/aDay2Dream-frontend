package com.example.aday2dream

import android.widget.LinearLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginPage(navController: NavController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text("aDay2Dream")
            TextFieldUsername()
            TextFieldPassword()
            Button(onClick = {
                navController.navigate("home")
            }) {
                Text("Log in");
            }
            CheckboxSignUp()
        }
    }

@Composable
fun TextFieldUsername() {
    var username by remember { mutableStateOf("") }

    TextField(
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") }
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

@Composable
fun RegisterTextButton()
{
    Row(){
        Text("Don't have an account?")
        Text(
            modifier = Modifier.clickable{

            },
            text = "Register now"
        )
    }
}

@Composable
fun CheckboxSignUp() {
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text("Keep me signed in")
    }
}
