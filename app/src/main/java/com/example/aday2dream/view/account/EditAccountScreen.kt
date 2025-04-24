package com.example.aday2dream.view.account

import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aday2dream.R
import com.example.aday2dream.view.Button
import com.example.aday2dream.view.TextField
import com.example.aday2dream.view.TopBar
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
        containerColor = colorResource(R.color.pink_secondary),
        topBar =
        {
            TopBar(
                label = "Edit Profile",
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colorResource(R.color.purple_main))
                    }
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Update your profile information:",
                style = TextStyle(fontSize = 18.sp),
                color = colorResource(R.color.purple_main)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth(),
                text = "Save Changes"
            )


            Button(
                text = "Delete Account",
                onClick = {
                    profile?.accountId?.let {
                        accountViewModel.deleteAccount(
                            accountId = it,
                            onSuccess = {
                                Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_LONG).show()
                                onNavigateLogin()
                            },
                            onError = {
                                Toast.makeText(context, "Error deleting account: $it", Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
