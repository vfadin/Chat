package com.vfadin.events.ui.login.forgotPassword.newPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.R
import com.vfadin.events.domain.entity.login.Captcha
import com.vfadin.events.ui.components.CaptchaImage
import com.vfadin.events.ui.components.ConfirmationDialog
import com.vfadin.events.ui.components.FormattedTextField
import com.vfadin.events.ui.components.WidthButton

@Composable
fun NewPasswordScreen(viewModel: NewPasswordViewModel, navController: NavHostController) {
    val dialogOpened = remember { mutableStateOf(false) }
    if (viewModel.state.isSuccess) {
        dialogOpened.value = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier.padding(top = 48.dp, bottom = 20.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { navController.navigateUp() }, modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left), contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.forgot_password_new_password_title),
                style = MaterialTheme.typography.h1
            )
        }
        ConfirmationDialog(opened = dialogOpened, onDismiss = {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        })
        if (viewModel.isError) {
            Text(
                stringResource(R.string.choose_user_error),
                style = MaterialTheme.typography.h1
            )
        } else {
            FormattedTextField(
                label = stringResource(R.string.new_password_label),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                value = viewModel.state.password,
                onValueChange = { viewModel.onEvent(NewPasswordEvent.NewPasswordChanged(it)) },
                error = viewModel.state.passwordError
            )
            FormattedTextField(
                label = stringResource(R.string.label_confirm_password),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                value = viewModel.state.passwordConfirmation,
                onValueChange = { viewModel.onEvent(NewPasswordEvent.ConfirmPasswordChanged(it)) },
                error = viewModel.state.passwordConfirmationError
            )
            FormattedTextField(
                label = stringResource(R.string.label_captcha),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                value = viewModel.state.captcha.value,
                onValueChange = { viewModel.onEvent(NewPasswordEvent.OnCaptchaTextChanged(it)) },
                error = viewModel.state.captchaError
            )
            WidthButton(
                label = "Восстановить",
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    viewModel.onEvent(NewPasswordEvent.SubmitClicked)
                })
        }
    }
}