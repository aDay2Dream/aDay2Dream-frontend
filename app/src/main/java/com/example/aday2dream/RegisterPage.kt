package com.example.aday2dream

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPage(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.clip(
                RoundedCornerShape(20.dp)).border(width = 2.dp, color = colorResource(R.color.purple_main), shape = RoundedCornerShape(20.dp))
            ,
            colors = TopAppBarColors(
                containerColor = colorResource(R.color.pink_secondary),
                scrolledContainerColor = colorResource(R.color.pink_secondary),
                navigationIconContentColor = colorResource(R.color.pink_secondary),
                titleContentColor = colorResource(R.color.pink_secondary),
                actionIconContentColor = colorResource(R.color.pink_secondary)
            ),
            title = {}
        )
        Row(modifier = Modifier.fillMaxWidth().padding(50.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom){
            Image(painter = painterResource(R.drawable.logo_name),
                contentDescription = stringResource(R.string.logo_content_description))

        }

    }) { innerPadding ->
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
                Text(stringResource(R.string.register_button_text));
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

