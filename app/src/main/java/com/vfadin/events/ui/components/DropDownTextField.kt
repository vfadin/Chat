package com.vfadin.events.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vfadin.events.R
import com.vfadin.events.ui.theme.BorderGray
import com.vfadin.events.ui.theme.Gray

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenuTextField(
    label: String,
    items: List<Pair<String, Int>>,
    dropCheckBox: Boolean = false,
    error: String = "",
    initialValue: String = "",
    onValueChange: (String) -> Unit = {},
    onItemSelected: (Pair<String, Int>) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    val iconRes = if (expanded)
        painterResource(R.drawable.ic_arrow_up)
    else
        painterResource(R.drawable.ic_arrow_down)
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { },
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    focusState.isFocused.let {
                        expanded = it
                        isFocused = it
                    }
                },
            value = selectedOption.ifEmpty {
                if (isFocused) "" else initialValue
            },
            onValueChange = {
                onValueChange(it)
                selectedOption = it
            },
            label = { Text(text = label, style = MaterialTheme.typography.body1) },
            trailingIcon = {
                Icon(iconRes, null, Modifier.clickable {
                    if (items.isNotEmpty()) {
                        expanded = !expanded
                    }
                })
            },
            isError = error.isNotEmpty(),
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
            maxLines = 1,
            textStyle = MaterialTheme.typography.body1
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.heightIn(min = 0.dp, max = 200.dp)
        ) {
            if (items.isNotEmpty())
                items.forEach { option ->
//                var checked by remember { mutableStateOf(false) }
//                if (selectedOption.contains(option.first)) {
//                    checked = true
//                }
                    DropdownMenuItem(onClick = {
//                    if (dropCheckBox) {
//                        checked = !checked
//                        if (checked) selectedOption += "$option "
//                        else selectedOption = selectedOption.substringBefore("$option ") +
//                                selectedOption.substringAfter("$option ")
//                    } else {
                        selectedOption = option.first
                        onItemSelected(option)
                        expanded = false
//                    }
                    }) {
//                    if (dropCheckBox) {
//                        Checkbox(checked = checked, onCheckedChange = { })
//                    }
                        Text(text = option.first, style = MaterialTheme.typography.body1)
                    }
                }
            else {
                DropdownMenuItem(onClick = {}) {
                    Text(text = "Ничего не найдено", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}