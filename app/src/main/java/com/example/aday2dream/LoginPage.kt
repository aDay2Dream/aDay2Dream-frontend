package com.example.aday2dream

import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginPage(navController: NavController) {
    Scaffold(){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text("aDay2Dream")
           // Spacer(Modifier.width(20.dp))
            TextFieldUsername()
            TextFieldPassword()
            Button(onClick = {
                navController.navigate("home")
            }) {
                Text("Log in");
            }
            CheckboxSignUp()
            RegisterTextButton(navController)
        }
    }
}



@Composable
fun TextFieldUsername() {
    var username by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.width(10.dp))
    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        value = username,
        onValueChange = { username = it },
        label = { Text("Username") }
    )
}

@Composable
fun TextFieldPassword() {
    var password by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.width(10.dp))
    OutlinedTextField(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        value = password,
        onValueChange = { password = it },
        label = { Text("Password")},
        visualTransformation = PasswordVisualTransformation()
    )
}

@SuppressLint("NewApi")
@Composable
fun RegisterTextButton(navController: NavController)
{
    Row(){
        Text("Don't have an account? ")
        Text(
            modifier = Modifier.clickable{
                navController.navigate("register")
            },
            text = "Register now",
            color = Color.Magenta
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
