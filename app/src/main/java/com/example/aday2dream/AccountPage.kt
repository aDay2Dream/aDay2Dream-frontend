package com.example.aday2dream

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.PackageManagerCompat.UnusedAppRestrictionsStatus
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountPage(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clip(
                    RoundedCornerShape(20.dp)
                ).border(width = 2.dp, color = colorResource(R.color.purple_main), shape = RoundedCornerShape(20.dp))
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
                    contentDescription = stringResource(R.string.logo_content_description)
                )

            }

        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp))
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.purple_main),
                        shape = RoundedCornerShape(20.dp)
                    ),
                containerColor = colorResource(R.color.pink_secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "account"
                        )
                    }
                }
            }
        }
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {

        }


        }
}


