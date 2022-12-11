package com.vfadin.events.ui.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vfadin.events.R
import java.util.*

@Composable
fun DateTextField(label: String, onDateChanged: (String) -> Unit = {}) {
    val context = LocalContext.current
    val date = remember { mutableStateOf("") }
    OutlinedTextField(
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calenar_line),
                null,
                modifier = Modifier.clickable {
                    datePickerFun(date, context, onDateChanged)
                }
            )
        },
        value = date.value,
        onValueChange = { },
        readOnly = true,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    datePickerFun(date = date, context = context)
                }
            },
        label = { Text(label, style = MaterialTheme.typography.body1) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = colorResource(R.color.border_gray),
            unfocusedBorderColor = colorResource(R.color.border_gray),
        ),
        textStyle = MaterialTheme.typography.body1,
    )
}

fun datePickerFun(date: MutableState<String>, context: Context, onDateChanged: (String) -> Unit = {}) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            date.value = "$day.${month + 1}.$year"
            if (day < 10) {
                if (month < 10) {
                    date.value = "0$day.0${month + 1}.$year"
                    onDateChanged(date.value)
                } else {
                    date.value = "0$day.${month + 1}.$year"
                    onDateChanged(date.value)
                }
            }
            else {
                if (month < 10) {
                    date.value = "$day.0${month + 1}.$year"
                    onDateChanged(date.value)
                } else {
                    date.value = "$day.${month + 1}.$year"
                    onDateChanged(date.value)
                }
            }
        }, year, month, day
    )
    datePickerDialog.show()
}