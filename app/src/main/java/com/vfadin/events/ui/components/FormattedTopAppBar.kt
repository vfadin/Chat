package com.vfadin.events.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vfadin.events.R
import com.vfadin.events.ui.theme.PlaceholderGray

@Composable
fun FormattedTopAppBar(
    title: String,
    backPressIconShown: Boolean = false,
    isNewIconNeed: Boolean = false,
    hideIcon: Boolean = true,
    newIconPainter: Painter = painterResource(id = R.drawable.ic_notification_fill),
    onBackIconClick: () -> Unit = {},
    onNewIconClick: () -> Unit = {},
    onBellIconClick: () -> Unit = {},
    onUserIconClick: () -> Unit = {},
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        elevation = 2.dp
    ) {
        val textStartPadding = if (backPressIconShown) TextStartPadding else 0.dp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backPressIconShown) {
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
            }
            Text(
                text = title,
                modifier = Modifier.padding(start = textStartPadding),
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isNewIconNeed) {
                IconButton(
                    onClick = {
                        onNewIconClick()
                    }
                ) {
                    Icon(
                        painter = newIconPainter,
                        contentDescription = null,
                        tint = PlaceholderGray
                    )
                }
            }
            if(!hideIcon){
                IconButton(
                    onClick = {
                        onBellIconClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_notification_fill),
                        contentDescription = null,
                        modifier = Modifier.size(16.sp.value.dp),
                        tint = PlaceholderGray
                    )
                }
                IconButton(
                    onClick = {
                        onUserIconClick()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_user_fill),
                        contentDescription = null,
                        modifier = Modifier.size(16.sp.value.dp),
                        tint = PlaceholderGray
                    )
                }
            }
        }
    }
}

private val TextStartPadding = 8.dp
