package com.example.aday2dream

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController)
{
    val scrollState = rememberScrollState()
    Scaffold( modifier = Modifier.background(colorResource(R.color.pink_secondary)),
        /*topBar = {
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

         */
        bottomBar = {
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
                        onClick = { navController.navigate("addpost") },
                        containerColor = colorResource(R.color.purple_main),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(modifier = Modifier.clip(
                            CircleShape).background(colorResource(R.color.pink_secondary)), imageVector = Icons.Default.Add, contentDescription =  "Localized description")
                    }
                    IconButton(onClick = {
                        navController.navigate("account")
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, contentDescription = "account", tint = colorResource(R.color.purple_main)
                        )
                    }
                }
            }
        }
)
    { innerPadding ->
    Column(
        modifier = Modifier
            .padding(innerPadding).background(colorResource(R.color.pink_secondary)).fillMaxHeight().verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
            PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        PostCard(Post(title = "Selling this", description = "aaaaaa", isActive = true, price = 40), navController)
        }
    }
}

@Composable
fun PostCard(post: Post, navController: NavController) {
    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).padding(10.dp).height(120.dp).clickable {
        navController.navigate("post")
    }) {
        Row(
            modifier = Modifier.fillMaxWidth()
        )
         {
            Column(
                modifier = Modifier.width(120.dp).clip(RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)),
                horizontalAlignment = AbsoluteAlignment.Left
            )
            {
                FloatingActionButton(
                    modifier = Modifier.fillMaxHeight().width(80.dp).clip(RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)),
                    onClick = {

                    }
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "play")
                }
            }
            Row()
            {
                Column() {
                    Text(post.title)
                    Text(post.description)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
                ){
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = AbsoluteAlignment.Right) {
                    Text(text = post.price.toString())
                }

            }
        }
    }
}


