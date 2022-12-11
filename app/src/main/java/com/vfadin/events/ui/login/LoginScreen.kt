package com.vfadin.events.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.R
import com.vfadin.events.ui.components.Tabs
import com.vfadin.events.ui.components.ConfirmationDialog
import com.vfadin.events.ui.login.registration.RegistrationTab
import com.vfadin.events.ui.theme.BorderGray

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    Box(contentAlignment = Alignment.Center) {
        if (viewModel.loginState.isLoginLoading || viewModel.registrationState.isRegistrationLoading) {
            CircularProgressIndicator(color = BorderGray)
        }
        if(viewModel.registrationState.isRegistrationSuccess) {
            LaunchedEffect(Unit) {
                viewModel.onRegistrationSuccess()
            }
        }
        if(viewModel.loginState.isLoginSuccess) {
            LaunchedEffect(Unit) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_sibsutis),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 48.dp, bottom = 20.dp).size(100.dp),
            )
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Tabs(
                listOf(
                    stringResource(R.string.login_left_tab),
                    stringResource(R.string.login_right_tab)
                ),
                viewModel.selectedTabIndexState
            )
            when (viewModel.selectedTabIndexState.value) {
                0 -> LoginTab(viewModel, navController)
                1 -> RegistrationTab(viewModel)
            }
            ConfirmationDialog(
                opened = viewModel.dialogOpened,
                stringResource(R.string.reg_confirmation_text)
            )
        }
    }
}