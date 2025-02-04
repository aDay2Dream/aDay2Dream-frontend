package com.example.aday2dream.view.account

import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aday2dream.R
import com.example.aday2dream.viewmodel.account.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccountScreen(
    onNavigateBack: () -> Unit,
    onNavigateLogin: () -> Unit,
    accountViewModel: AccountViewModel
) {
    val profile by accountViewModel.profile.observeAsState()
    var firstName by remember { mutableStateOf(profile?.firstName ?: "") }
    var lastName by remember { mutableStateOf(profile?.lastName ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var username by remember { mutableStateOf(profile?.username ?: "") }
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.pink_secondary)),
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.purple_main)
                )
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Update your profile information:",
                style = TextStyle(fontSize = 18.sp, color = Color.Black)
            )

            BasicTextField(
                value = firstName,
                onValueChange = { firstName = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            BasicTextField(
                value = lastName,
                onValueChange = { lastName = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            BasicTextField(
                value = email,
                onValueChange = { email = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            BasicTextField(
                value = username,
                onValueChange = { username = it },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
            )

            Button(
                onClick = {
                    profile?.let {
                        profile!!.accountId?.let { it1 ->
                            accountViewModel.updateProfile(
                                accountId = it1,
                                password = profile!!.password,
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                username = username,
                                onSuccess = {
                                    Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_LONG).show()
                                    onNavigateBack()
                                },
                                onError = {
                                    Toast.makeText(context, "Error updating profile: $it", Toast.LENGTH_LONG).show()
                                    Log.d("Edit Account Screen", it)
                                }
                            )
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.purple_main)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }

            Button(
                onClick = {
                    accountViewModel.deleteAccount(
                        onSuccess = {
                            Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_LONG).show()
                            onNavigateLogin()
                        },
                        onError = {
                            Toast.makeText(context, "Error deleting account: $it", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete Account")
            }
        }
    }
}
