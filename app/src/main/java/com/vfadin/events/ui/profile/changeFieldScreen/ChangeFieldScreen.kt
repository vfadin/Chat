@file:OptIn(ExperimentalMaterial3Api::class)

package com.vfadin.events.ui.profile.changeFieldScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.ui.components.BottomNavBar
import com.vfadin.events.ui.components.FormattedTextField
import com.vfadin.events.ui.components.FormattedTopAppBar
import com.vfadin.events.R

@Composable
fun ChangeFieldScreen(
    title: String,
    navController: NavHostController,
    viewModel: ChangeFieldViewModel
) {
    Scaffold(
        topBar = { AppTopBar(title, viewModel) },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            FormattedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = title,
                singleLine = true,
                value = viewModel.fieldValue,
                onValueChange = { viewModel.fieldValue = it }
            )
            Text(text = "Тут требования текстом")
        }
    }
}

@Composable
private fun AppTopBar(
    title: String,
    viewModel: ChangeFieldViewModel
) {
    FormattedTopAppBar(
        title = title,
        backPressIconShown = true,
        hideIcon = true,
        isNewIconNeed = true,
        newIconPainter = painterResource(id = R.drawable.ic_done),
        onNewIconClick = {
            if (title.contains("nick", true)) {
                viewModel.changeNickname()
            } else {
                viewModel.changeName()
            }
        })
}
