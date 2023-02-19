package com.vfadin.events.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.vfadin.events.ui.components.BottomNavBar
import com.vfadin.events.ui.theme.BorderGray
import com.vfadin.events.ui.theme.PlaceholderGray
import com.vfadin.events.ui.theme.SurfaceGray

@Composable
fun ChatScreen(viewModel: ChatViewModel, navController: NavHostController) {
    Scaffold(
        topBar = { ChatAppBar(viewModel) },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item { Message() }
            item { SendMessageRow() }
        }
    }
}

@Composable
fun SendMessageRow() {
    Row() {
//        OutlinedTextField(value = , onValueChange = )
    }
}

@Composable
fun Message() {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(start = 16.dp, end = 4.dp)
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
        Column(
            Modifier
                .padding(top = 16.dp)
                .background(SurfaceGray)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Иван Иванов",
                fontSize = 14.sp,
                color = Color.Blue
            )
            Text(
                text = "Привет, как дела?",
                fontSize = 14.sp,
                color = BorderGray
            )
        }
    }
}

@Composable
private fun ChatAppBar(
    viewModel: ChatViewModel,
    onBackIconClick: () -> Unit = {},
    onUserClick: () -> Unit = {},
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
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
            Row(Modifier.padding(top = 4.dp)) {
                GlideImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp),
                    imageModel = viewModel.appBarState.avatarUrl,
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
                        text = viewModel.appBarState.name,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = if (viewModel.appBarState.memberCount > 2)
                            "${viewModel.appBarState.memberCount} участников" else
                            if (viewModel.appBarState.isOnline) "Онлайн" else "Офлайн",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Box(modifier = Modifier.size(24.dp))
        }
    }
}