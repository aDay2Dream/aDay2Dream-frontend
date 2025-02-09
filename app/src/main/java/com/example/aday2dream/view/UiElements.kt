package com.example.aday2dream.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aday2dream.R
import com.example.aday2dream.model.dto.PostDto
import com.example.aday2dream.model.dto.PromptDto
import com.example.aday2dream.viewmodel.post.PostViewModel


@Composable
fun PostItem(
    post: PostDto,
    navigateToPost: (postId: Long) -> Unit
) {
    Log.d("Post Item", "postId: ${post.postId}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .height(120.dp)
            .clickable {
                post.postId?.let { navigateToPost(it) } ?: Log.e("PostItem", "Post ID is null")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardColors(
            containerColor = colorResource(R.color.purple_main),
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(15.dp)),
                onClick = { /* Play logic */ },
                containerColor = colorResource(R.color.pink_secondary)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "$${post.price}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}



@Composable
fun EditPostItem(post: PostDto,
                 onEditPost: (postId: Long) -> Unit,
                 onViewPrompt: (postId: Long) -> Unit,
                 postViewModel: PostViewModel,
                 onNavigateBack: () -> Unit) {
    Log.d("Edit Post Item", post.postId.toString())
    val error by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { post.postId?.let { onViewPrompt(post.postId) } }),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(
            containerColor = colorResource(R.color.pink_secondary),
            contentColor = colorResource(R.color.purple_main),
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium)
            Text(text = post.description, style = MaterialTheme.typography.bodyMedium)
            Row(horizontalArrangement = Arrangement.SpaceEvenly){
                Button(modifier = Modifier.width(140.dp), onClick = { post.postId?.let { onEditPost(post.postId) } }, text = "Edit Post")
                Spacer(Modifier.width(10.dp))
                Button(modifier = Modifier.width(140.dp), onClick = { post.postId?.let {
                    postViewModel.deletePost(postId = it, onSuccess = {
                        Log.d("Delete Post", "Post Deleted")
                        onNavigateBack()
                    }, onError = {})
                } },
                    text = "Delete Post"
                )
            }
        }
    }
}


@Composable
fun PromptItem(prompt: PromptDto, onViewPrompt: (promptId: Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = { prompt.promptId?.let { onViewPrompt(it) } })
            .background(color = colorResource(R.color.pink_secondary)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(
            containerColor = colorResource(R.color.pink_secondary),
            contentColor = colorResource(R.color.purple_main),
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = prompt.promptTitle, style = MaterialTheme.typography.titleMedium, color = colorResource(R.color.purple_main))
            Text(text = prompt.promptDescription, style = MaterialTheme.typography.bodyMedium, color = colorResource(R.color.purple_main))
            // Button(onClick = { prompt.promptId?.let { onViewPrompt(it) } }, text = "View Prompt")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    painter: Painter? = null,
    label: String = "",
    navigationIcon: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(4.dp, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .background(colorResource(R.color.pink_secondary)),
        title = {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.logo_content_description),
                    modifier = Modifier.size(150.dp),
                )
            }
            else
            {
                Text(
                    text = label,
                    color = colorResource(R.color.purple_main)
                )
            }
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.pink_secondary),
            titleContentColor = colorResource(R.color.purple_main)
        )
    )
}

@Composable
fun HomeBottomBar(navigateToAddPost: () -> Unit, navigateToProfile: () -> Unit) {
    BottomAppBar(
        modifier = Modifier
            .height(80.dp)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .shadow(4.dp),
        containerColor = colorResource(R.color.pink_secondary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = colorResource(R.color.purple_main),
                    modifier = Modifier.size(28.dp)
                )
            }
            FloatingActionButton(
                onClick = navigateToAddPost,
                containerColor = colorResource(R.color.purple_main),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Post",
                    tint = colorResource(R.color.pink_secondary)
                )
            }
            IconButton(onClick = navigateToProfile) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = colorResource(R.color.purple_main),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    error: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    width = 2.dp,
                    color = if (error != null) Color.Red else if (isFocused) colorResource(R.color.purple_main) else colorResource(R.color.purple_main),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            label = { if (label.isNotEmpty()) Text(text = label, color = colorResource(R.color.purple_main)) },
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder, color = colorResource(R.color.purple_main)) },
            isError = error != null,
            singleLine = true,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(R.color.purple_main),
                unfocusedBorderColor = colorResource(R.color.purple_main),
                cursorColor = colorResource(R.color.purple_main),
                errorBorderColor = Color.Red,
                focusedSupportingTextColor = Color.Black,
                focusedTextColor = colorResource(R.color.purple_main),
                unfocusedTextColor = colorResource(R.color.purple_main)
            ),
            leadingIcon = leadingIcon?.let {
                { Icon(imageVector = it, contentDescription = null, tint = colorResource(R.color.purple_main)) }
            },
            trailingIcon = trailingIcon?.let {
                {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(imageVector = it, contentDescription = null, tint = colorResource(R.color.purple_main))
                    }
                }
            },
            visualTransformation = visualTransformation
        )

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    buttonStyle: ButtonStyle = ButtonStyle.Filled
) {
    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (buttonStyle) {
                ButtonStyle.Filled -> colorResource(R.color.purple_main)
                ButtonStyle.Outlined, ButtonStyle.Text -> Color.Transparent
            },
            contentColor = if (buttonStyle == ButtonStyle.Filled) Color.White else colorResource(R.color.purple_main),
            disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (buttonStyle == ButtonStyle.Outlined) {
            BorderStroke(2.dp, colorResource(R.color.purple_main))
        } else null
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                icon?.let {
                    Icon(imageVector = it, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun RegisterTextButton(onNavigateToRegister: () -> Unit)
{
    Row(){
        Text(
            stringResource(R.string.no_account)
        )
        Text(
            modifier = Modifier.clickable{
                onNavigateToRegister()
            },
            text = stringResource(R.string.register_button_text),
            color = colorResource(R.color.purple_secondary)
        )
    }
}


enum class ButtonStyle {
    Filled, Outlined, Text
}



