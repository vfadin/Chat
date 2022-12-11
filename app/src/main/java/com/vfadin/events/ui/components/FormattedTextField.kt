package com.vfadin.events.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vfadin.events.R
import com.vfadin.events.ui.theme.BorderGray
import com.vfadin.events.ui.theme.Gray

@Composable
fun FormattedTextField(
    label: String,
    modifier: Modifier = Modifier,
    value: String,
    error: String = "",
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = singleLine,
            isError = error.isNotEmpty(),
            label = { Text(text = label, style = MaterialTheme.typography.body1) },
            modifier = Modifier
                .padding(bottom = 2.dp)
                .then(modifier),
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
                disabledTextColor = Gray
            ),
            textStyle = MaterialTheme.typography.body1,
            trailingIcon = {
                if (error.isNotEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = MaterialTheme.colors.error
                    )
                }
            }
        )
        if (error.isNotEmpty()) {
            ErrorTextRow(text = error)
        }
    }
}