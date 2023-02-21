package com.vfadin.events.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.vfadin.events.R
import com.vfadin.events.domain.entity.Message
import com.vfadin.events.ui.theme.PlaceholderGray
import com.vfadin.events.ui.theme.SurfaceGray

@Composable
fun ChatScreen(viewModel: ChatViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { ChatAppBar(viewModel) { navController.navigateUp() } }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            LazyColumn(contentPadding = padding, modifier = Modifier.fillMaxWidth()) {
                items(viewModel.messageState) {
                    Message(it, it.author == viewModel.chatState.currentUser.id)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colors.background)
            ) {
                SendMessageRow()
            }
        }
    }
}

@Composable
fun SendMessageRow() {
    OutlinedTextField(value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        trailingIcon = {
            Icon(Icons.Default.Send, null)
        },
        placeholder = { Text(text = "Message...") }
    )
}

@Composable
fun Message(message: Message, isYourMessage: Boolean) {
    Row(verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .padding(
                start = if (isYourMessage) 24.dp else 0.dp,
                end = if (isYourMessage) 0.dp else 24.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = if (isYourMessage) Arrangement.End else Arrangement.Start
    ) {
//        GlideImage(
//            imageModel = "https://i.pinimg.com/originals/7b/0d/7b/7b0d7b8b1b0d1b1b1b1b1b1b1b1b1b1b.jpg",
//            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
//            failure = {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_account_circle),
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .size(24.dp),
//                    contentScale = ContentScale.Fit,
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(Color.Gray)
//                )
//            },
//            modifier = Modifier
//                .size(24.dp)
//                .clip(CircleShape)
//        )
        Box(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isYourMessage) SurfaceGray else PlaceholderGray)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                text = message.text,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun ChatAppBar(
    viewModel: ChatViewModel,
    onBackIconClick: () -> Unit = {},
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { onBackIconClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.size(16.sp.value.dp),
                    tint = PlaceholderGray
                )
            }
            Row(Modifier
                .padding(top = 4.dp)
                .align(Alignment.Center)) {
                GlideImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp),
                    imageModel = viewModel.chatState.avatarUrl,
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                    failure = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_account_circle),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(64.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Gray)
                        )
                    }
                )
                Column(Modifier.padding(start = 24.dp)) {
                    Text(
                        text = viewModel.chatState.name,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = if (viewModel.chatState.memberCount > 2)
                            "${viewModel.chatState.memberCount} участников" else
                            if (viewModel.chatState.isOnline) "Онлайн" else "Офлайн",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Box(modifier = Modifier.size(24.dp))
        }
    }
}