package com.example.aday2dream.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aday2dream.viewmodel.AccountViewModel
import com.example.aday2dream.R
import com.example.aday2dream.model.Account


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit, onRegisterSuccess: () -> Unit, viewModel: AccountViewModel) {
    var account by remember { mutableStateOf(Account()) }
    var isLoading by remember { mutableStateOf(false) }
    var registrationMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var errorMessage = remember { mutableStateOf("") }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
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
                NameField(
                    value = account.firstName,
                    onChange = { data -> account = account.copy(firstName = data) },
                    modifier = Modifier.width(150.dp),
                    label = "First Name"
                )
                NameField(
                    value = account.lastName,
                    onChange = { data -> account = account.copy(lastName = data) },
                    modifier = Modifier.width(150.dp),
                    label = "Last Name"

                )
            }
            NameField(
                value = account.email,
                onChange = { data -> account = account.copy(email = data) },
                modifier = Modifier.width(150.dp),
                label = "E-mail"
            )

            LoginField(
                value = account.username,
                onChange = { data -> account = account.copy(username = data) },
                modifier = Modifier.width(150.dp)
            )
            PasswordField(
                value = account.password,
                onChange = {data -> account = account.copy(password = data)},
                modifier = Modifier.width(150.dp)
            )

            Button(
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
                })
             {
                Text(stringResource(R.string.register_button_text));
            }
        }
    }
}

@Composable
fun TextFieldFirstName()
{
    var firstName by remember { mutableStateOf("") }


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

@Composable
fun NameField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
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




