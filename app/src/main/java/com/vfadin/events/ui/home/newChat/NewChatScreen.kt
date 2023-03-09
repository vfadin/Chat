package com.vfadin.events.ui.home.newChat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.vfadin.events.R
import com.vfadin.events.ui.components.FormattedTopAppBar
import com.vfadin.events.ui.theme.BlueTransparent

@Composable
fun NewChatScreen(viewModel: NewChatViewModel) {
    Scaffold(
        topBar = { AppTopBar(viewModel) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            items(viewModel.users) { user ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            viewModel.users = viewModel.users.map {
                                if (it.id == user.id) {
                                    it.copy(isSelected = !it.isSelected)
                                } else {
                                    it
                                }
                            }
                        }) {
                    Row(
                        modifier = Modifier
                            .background(if (user.isSelected) BlueTransparent else Color.Transparent)
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.padding(horizontal = 16.dp)) {
                            GlideImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(32.dp),
                                imageModel = user.avatar,
                                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                                failure = {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_account_circle),
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(32.dp),
                                        contentScale = ContentScale.Fit,
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(Color.Gray)
                                    )
                                }
                            )
                        }
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = user.name,
                            style = MaterialTheme.typography.h2
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun AppTopBar(viewModel: NewChatViewModel) {
    FormattedTopAppBar(
        title = "Новый чат",
        isNewIconNeed = true,
        newIconPainter = painterResource(id = R.drawable.ic_done),
        onNewIconClick = { viewModel.createChat() }
    )
}