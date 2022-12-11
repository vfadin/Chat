package com.vfadin.events.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.vfadin.events.ui.theme.BorderGray
import com.vfadin.events.ui.theme.Gray

@Composable
fun PasswordTextField(
    label: String,
    modifier: Modifier,
    value: String,
    error: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }
    OutlinedTextField(
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label, style = MaterialTheme.typography.body1) },
        modifier = modifier,
        colors = if (error.isEmpty()) TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = BorderGray,
            unfocusedBorderColor = BorderGray,
            focusedBorderColor = Gray,
            unfocusedLabelColor = Gray,
            focusedLabelColor = Gray,
            textColor = Gray,
            disabledTextColor = Gray
        ) else TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = MaterialTheme.colors.error,
            unfocusedBorderColor = MaterialTheme.colors.error,
            focusedBorderColor = Gray,
            unfocusedLabelColor = Gray,
            focusedLabelColor = Gray,
            textColor = Gray,
            disabledTextColor = Gray,
            trailingIconColor = MaterialTheme.colors.error
        ),
        textStyle = MaterialTheme.typography.body1,
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    tint = if (error.isEmpty()) BorderGray else MaterialTheme.colors.error
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
    if (error.isNotEmpty()) {
        ErrorTextRow(text = error)
    }
}