package com.vfadin.events.ui.login.forgotPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.R
import com.vfadin.events.domain.entity.login.Captcha
import com.vfadin.events.ui.components.CaptchaImage
import com.vfadin.events.ui.components.ConfirmationDialog
import com.vfadin.events.ui.components.FormattedTextField
import com.vfadin.events.ui.components.WidthButton
import com.vfadin.events.ui.theme.Blue
import com.vfadin.events.ui.theme.BorderGray

@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel, navController: NavHostController) {
    val dialogOpened = remember { mutableStateOf(false) }
    if (viewModel.state.isSendSuccess) {
        dialogOpened.value = true
    }
    ConfirmationDialog(
        opened = dialogOpened,
        title = stringResource(R.string.forgot_password_dialog_title),
        text = stringResource(R.string.forgot_password_dialog_text),
        textColor = Blue,
        onDismiss = { navController.navigate("login") }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(color = BorderGray)
        }
        Image(
            painter = painterResource(R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 20.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(R.string.forgot_password_title),
                style = MaterialTheme.typography.h1
            )
        }
        FormattedTextField(
            label = stringResource(R.string.forgot_password_email),
            modifier = Modifier
                .fillMaxWidth(),
            value = viewModel.state.email,
            onValueChange = { viewModel.onEvent(ForgotPasswordEvent.EmailChanged(it))},
            error = viewModel.state.emailError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        WidthButton(label = "Восстановить", modifier = Modifier.padding(vertical = 8.dp)) {
            viewModel.onEvent(ForgotPasswordEvent.SendClicked)
        }
    }

}