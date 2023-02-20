package com.vfadin.events.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.vfadin.events.domain.entity.Chat
import com.vfadin.events.ui.components.BottomNavBar
import com.vfadin.events.ui.components.CustomTextField
import com.vfadin.events.ui.theme.PlaceholderGray

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    val chats by viewModel.chatsStateFlow.collectAsState(initial = emptyList())
    LaunchedEffect(Unit) {
        viewModel.getChats()
    }
    Scaffold(
        topBar = { AppTopBar(viewModel) },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            items(chats) {
                ChatItem(chat = it) {
                    navController.navigate("chat_screen/${it.id}")
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, onClick: () -> Unit = {}) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                onClick()
            }
    ) {
        GlideImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            imageModel = chat.avatarUrl,
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.ic_account_circle),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        )
        Column(Modifier.padding(start = 24.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = chat.name,
                style = MaterialTheme.typography.h3
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = if (chat.lastMessage.text.length > 55)
                        "${chat.lastMessage.text.substring(0, 55)}..."
                    else if (chat.lastMessage.text.isEmpty()) "Чат создан"
                    else chat.lastMessage.text,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = chat.updatedAt.substringBefore("T"),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    Divider()
}

@Composable
private fun AppTopBar(viewModel: HomeViewModel) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!viewModel.isSearchVisible.value) {
                Text(
                    text = "Главная",
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                CustomTextField(
                    value = viewModel.searchState.value,
                    onValueChange = { viewModel.searchState.value = it },
                    placeHolderString = "Поиск",
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone  = {
                            viewModel.search()
                        }
                    )
                )
            }
            IconButton(
                onClick = {
                    viewModel.isSearchVisible.value = !viewModel.isSearchVisible.value
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(16.sp.value.dp),
                    tint = PlaceholderGray
                )
            }
            IconButton(
                onClick = {
                    viewModel.navigateToNewChat()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(16.sp.value.dp),
                    tint = PlaceholderGray
                )
            }
        }
    }
}