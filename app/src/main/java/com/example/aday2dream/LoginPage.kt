package com.example.aday2dream

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.LinearLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginPage(navController: NavController) {
    var scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var credentials by remember { mutableStateOf(Credentials()) }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
        modifier = Modifier.background(colorResource(R.color.pink_secondary))){
            innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Image(modifier = Modifier.size(150.dp, 150.dp), painter = painterResource(R.drawable.logo), contentDescription = stringResource(id = R.string.logo_content_description))
            Spacer(Modifier.height(20.dp))
            LoginField(
                value = credentials.username,
                onChange = { data -> credentials = credentials.copy(username = data) },
                modifier = Modifier.width(300.dp)
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },

                modifier = Modifier.width(300.dp)
            )
            Spacer(Modifier.height(20.dp))
            Button(modifier = Modifier.size(100.dp, 50.dp), onClick = {
                if(credentials.isNotEmpty() && credentials.username == "admin" && credentials.password == "aaa"){
                    navController.navigate("home")
                }
                else
                {
                    scope.launch {
                        snackbarHostState.showSnackbar("Wrong username or password")
                    }
                }
            }) {
                Text(stringResource(R.string.login_button_text));
            }
            /*
            LabeledCheckbox(
                label = "Remember me",
                onCheckChanged = { credentials = credentials.copy(remember = !credentials.remember) },
                isChecked = credentials.remember
            )
             */
            RegisterTextButton(navController)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.username),
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
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
) {

    var isPasswordVisible by remember { mutableStateOf(false) }


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),

        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@SuppressLint("NewApi")
@Composable
fun RegisterTextButton(navController: NavController)
{
    Row(){
        Text(
            stringResource(R.string.no_account)
        )
        Text(
            modifier = Modifier.clickable{
                navController.navigate("register")
            },
            text = stringResource(R.string.register_button_text),
            color = colorResource(R.color.purple_secondary)
        )
    }
}

@Composable
fun LabeledCheckbox(
    label: String,
    onCheckChanged: () -> Unit,
    isChecked: Boolean
) {

    Row(
        Modifier
            .clickable(
                onClick = onCheckChanged
            )
            .padding(4.dp)
    ) {
        Checkbox(checked = isChecked, onCheckedChange = null)
        Spacer(Modifier.size(6.dp))
        Text(label)
    }
}
