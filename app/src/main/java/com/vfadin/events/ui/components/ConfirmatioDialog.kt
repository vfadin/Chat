package com.vfadin.events.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vfadin.events.R
import com.vfadin.events.ui.theme.Blue
import com.vfadin.events.ui.theme.TextBlack
import com.vfadin.events.ui.theme.Transparent
import com.vfadin.events.ui.theme.White

@Composable
fun ConfirmationDialog(
    opened: MutableState<Boolean>,
    title: String = "",
    text: String = "",
    textColor: Color = TextBlack,
    onDismiss: () -> Unit = {},
) {
    if (opened.value)
        AlertDialog(
            onDismissRequest = {
                opened.value = false
                onDismiss()
            },
            title = {
                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        backgroundColor = Blue,
                        contentColor = White,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_mail_line),
                            contentDescription = null,
                            Modifier
                                .padding(8.dp)
                        )
                    }
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        color = TextBlack,
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Text(
                        text = text,
                        color = textColor,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            },
            buttons = {
                Divider()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Button(
                        onClick = {
                            opened.value = false
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Transparent,
                            contentColor = TextBlack
                        ),
                        elevation = null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.lable_its_clear),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        )
}

@Preview
@Composable
fun ConfirmationDialogPreview() {
    val opened = remember { mutableStateOf(true) }
    ConfirmationDialog(
        opened = opened,
        title = stringResource(R.string.reg_confirmation_text),
        text = ""
    )
}