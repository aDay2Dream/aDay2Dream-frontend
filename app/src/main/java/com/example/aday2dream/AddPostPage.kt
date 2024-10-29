package com.example.aday2dream

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPostPage(navController: NavController)
{
    Scaffold()
    {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){



        }
    }
}

@Composable
fun TitleTextField(){

    var title by remember { mutableStateOf("") }

    OutlinedTextField(

        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        value = title,
        onValueChange = { title = it },
        label = { Text(stringResource(R.string.title_post)) },
    )
}

@Composable
fun DescriptionTextField(){
    var description by remember { mutableStateOf("") }

    OutlinedTextField(

        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        value = description,
        onValueChange = { description = it },
        label = { Text(stringResource(R.string.description_post)) },
    )
}