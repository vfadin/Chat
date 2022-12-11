package com.vfadin.events.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vfadin.events.R
import com.vfadin.events.ui.components.ClickableBlueText
import com.vfadin.events.ui.components.WidthButton
import com.vfadin.events.ui.theme.Blue
import com.vfadin.events.ui.theme.IconButtonGray

val emails = listOf("21321321@ngs.ru", "21321321@ngs.ru")
val em = "m***@mail.ru"

@Composable
fun AccExistScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = IconButtonGray
            ) {
                IconButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { navController.navigateUp() }) {
                    Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
                }
                Text(
                    text = stringResource(R.string.acc_exist_screen_title),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            LazyColumn(Modifier.padding(16.dp)) {
                item {
                    Text(
                        buildAnnotatedString {
                            append(stringResource(R.string.login_screen_tip))
                            withStyle(SpanStyle(Blue)) {
                                append(" $em")
                            }
                        }
                    )
                }
                items(emails) { email ->
                    UserCard(email)
                }
            }
            WidthButton(label = "Меня нет в этом списке", modifier = Modifier.padding(16.dp)) {

            }
        }
    }
}


@Composable
fun UserCard(email: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = email,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.Bold
        )
        ClickableBlueText("Это я", modifier = Modifier.padding(8.dp)) {}
    }
}