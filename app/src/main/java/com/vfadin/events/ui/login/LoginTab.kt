package com.vfadin.events.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.R
import com.vfadin.events.ui.components.*

@Composable
fun LoginTab(viewModel: LoginViewModel, navController: NavHostController) {
    FormattedTextField(
        label = stringResource(R.string.label_login),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        singleLine = true,
        value = viewModel.loginState.login,
        onValueChange = { viewModel.onLoginEvent(LoginEvent.LoginChanged(it)) }
    )
    PasswordTextField(
        label = stringResource(R.string.label_password),
        modifier = Modifier
            .fillMaxWidth(),
        value = viewModel.loginState.password,
        onValueChange = { viewModel.onLoginEvent(LoginEvent.PasswordChanged(it)) },
    )
    if (viewModel.loginState.isLoginError) {
        Toast.makeText(
            LocalContext.current,
            stringResource(R.string.login_error),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.toastShown()
    }

    WidthButton(stringResource(R.string.label_enter), Modifier.padding(vertical = 8.dp)) {
        viewModel.onLoginEvent(LoginEvent.LoginClicked)
    }
    ClickableBlueText(
        text = stringResource(R.string.label_forgot_password),
        modifier = Modifier.padding(vertical = 8.dp),
        onClick = { navController.navigate("forgot_password") }
    )
}