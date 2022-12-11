package com.vfadin.events.ui.login.registration

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vfadin.events.R
import com.vfadin.events.ui.components.*
import com.vfadin.events.ui.login.LoginViewModel

@Composable
fun RegistrationTab(viewModel: LoginViewModel) {
    FormattedTextField(
        label = stringResource(R.string.label_login), modifier = Modifier
            .fillMaxWidth(),
        onValueChange = { viewModel.onRegistrationEvent(RegistrationEvent.LoginChanged(it)) },
        value = viewModel.registrationState.login,
        error = viewModel.registrationState.loginError
    )
    FormattedTextField(
        label = stringResource(R.string.label_email), modifier = Modifier
            .fillMaxWidth(),
        onValueChange = { viewModel.onRegistrationEvent(RegistrationEvent.EmailChanged(it)) },
        value = viewModel.registrationState.email,
        error = viewModel.registrationState.emailError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    PasswordTextField(
        label = stringResource(R.string.label_password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onValueChange = { viewModel.onRegistrationEvent(RegistrationEvent.PasswordChanged(it)) },
        value = viewModel.registrationState.password,
        error = viewModel.registrationState.passwordError,
    )
    PasswordTextField(
        label = stringResource(R.string.label_confirm_password),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onValueChange = {
            viewModel.onRegistrationEvent(RegistrationEvent.PasswordConfirmationChanged(it))
        },
        value = viewModel.registrationState.passwordConfirmation,
        error = viewModel.registrationState.passwordConfirmationError
    )
    WidthButton(
        stringResource(R.string.label_registration),
        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
    ) {
        viewModel.onRegistrationEvent(RegistrationEvent.RegistrationClicked)
    }
}