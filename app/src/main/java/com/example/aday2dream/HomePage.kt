package com.example.aday2dream

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController)
{
    Scaffold( modifier = Modifier.background(colorResource(R.color.pink_third)),
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

        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp)).border(width = 2.dp, color = colorResource(R.color.purple_main), shape = RoundedCornerShape(20.dp)),
                containerColor = colorResource(R.color.pink_secondary)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search, contentDescription = "account"
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier.clip(CircleShape).background(colorResource(R.color.purple_main)),
                        onClick = { /* do something */ },
                        containerColor = colorResource(R.color.purple_main),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(modifier = Modifier.clip(
                            CircleShape).background(colorResource(R.color.pink_secondary)), imageVector =  Icons.Default.Add, contentDescription =  "Localized description")
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, contentDescription = "account"
                        )
                    }
                }
            }
        }
)
    { innerPadding ->
    Column(
        modifier = Modifier
            .padding(innerPadding).background(colorResource(R.color.pink_secondary)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {}
    }
}

@Composable
fun PostCard() {
    Card() {
        Column()
        {
            Row()
            {
                Text("Username")
                Text("Description")
                Text("Price")
            }
            Row()
            {

            }
        }
    }
}

