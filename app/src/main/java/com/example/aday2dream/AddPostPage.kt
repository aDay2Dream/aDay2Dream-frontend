package com.example.aday2dream

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigator
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostPage(navController: NavController)
{
    var post by remember { mutableStateOf(Post(createdAt = LocalDateTime.now())) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
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

               }


        /*bottomBar = {
            BottomAppBar(modifier = Modifier.height(120.dp).clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)).border(width = 2.dp, color = colorResource(R.color.purple_main), shape = RoundedCornerShape(20.dp)),
                containerColor = colorResource(R.color.pink_secondary)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "account", tint = colorResource(R.color.purple_main)
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier.clip(CircleShape).background(colorResource(R.color.purple_main)),
                        onClick = { navController.navigate("post_page")},
                        containerColor = colorResource(R.color.purple_main),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(modifier = Modifier.clip(
                            CircleShape
                        ).background(colorResource(R.color.pink_secondary)), imageVector =  Icons.Default.Add, contentDescription =  "Localized description")
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, contentDescription = "account", tint = colorResource(R.color.purple_main)
                        )
                    }
                }
            }
        }

         */
    )
    {innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            TitleTextField(
                modifier = Modifier.width(300.dp),
                value = post.title,
                onChange = {data -> post = post.copy(title = data)}
            )
            Spacer(modifier = Modifier.width(10.dp))
            DescriptionTextField(
                modifier = Modifier.width(300.dp).height(200.dp),
                value = post.description,
                onChange = {data -> post = post.copy(description = data)}
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(modifier = Modifier.size(150.dp, 50.dp), onClick = {
                    if(post.isNotEmpty())
                    {
                        scope.launch {
                            snackbarHostState.showSnackbar("Post created!")
                        }
                        navController.navigate("home")
                    }
            }){
                Text("Add post!")
            }
        }
    }
}




@Composable
fun TitleTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.title_post),
) {

    val focusManager = LocalFocusManager.current


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun DescriptionTextField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.description_post),
) {

    val focusManager = LocalFocusManager.current


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        label = { Text(label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}