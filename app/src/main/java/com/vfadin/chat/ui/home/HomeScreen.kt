package com.vfadin.chat.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vfadin.chat.R
import com.vfadin.chat.ui.theme.ChatTheme

@Composable
fun Home() {
    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val appThemeColor = MaterialTheme.colorScheme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = appThemeColor.surfaceVariant,
                        titleContentColor = appThemeColor.onSurfaceVariant
                    ),
                    title = { Text(text = stringResource(R.string.home_screen_title)) }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MessagesList()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Message") },
                        value = "",
                        shape = Shapes.Full,
                        onValueChange = {
                            /*TODO*/
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(4.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessagesList() {
    LazyColumn {
        items(listOf("Hello!", "What's up?")) { message ->
            MessageCard(message)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageCard(message: String) {
    Card(modifier = Modifier.padding(16.dp, 4.dp)) {
        Text(
            text = message,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChatTheme {
        HomeScreen()
    }
}