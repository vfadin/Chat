//package com.vfadin.events.ui.login.forgotPassword.chooseUser
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.RadioButton
//import androidx.compose.material.RadioButtonDefaults
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
//import com.vfadin.events.R
//import com.vfadin.events.ui.components.WidthButton
//import com.vfadin.events.ui.theme.Blue
//import com.vfadin.events.ui.theme.BorderGray
//import com.vfadin.events.ui.theme.Gray
//import com.vfadin.events.ui.theme.TextBlack
//
//@Composable
//fun ChooseUserScreen(viewModel: ChooseUserViewModel, navController: NavController) {
//    val userState = viewModel.userResetPasswordFlow.collectAsState(initial = listOf())
//    val swipeRefreshState by viewModel.isRefreshing
//    SwipeRefresh(
//        state = rememberSwipeRefreshState(swipeRefreshState),
//        onRefresh = { viewModel.getUsers() },
//        swipeEnabled = true,
//        indicator = { state, trigger ->
//            SwipeRefreshIndicator(
//                state = state,
//                refreshTriggerDistance = trigger,
//                scale = true,
//            )
//        }
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            item {
//                Image(
//                    painter = painterResource(R.drawable.ic_app),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(top = 48.dp, bottom = 20.dp),
//                )
//            }
//            if (viewModel.isError.value) {
//                item {
//                    Text(
//                        stringResource(R.string.choose_user_error),
//                        style = MaterialTheme.typography.h1
//                    )
//                }
//            } else {
//                item {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 16.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = stringResource(R.string.forgot_password_choose_user_title),
//                            style = MaterialTheme.typography.h1,
//                        )
//                    }
//                }
//                items(userState.value) { user ->
//                    UserRow(user, viewModel)
//                }
//                item {
//                    WidthButton(
//                        label = stringResource(R.string.label_change_password),
//                        modifier = Modifier.padding(vertical = 16.dp)
//                    ) {
//                        if (viewModel.chosenUserId != -1)
//                            navController.navigate(
//                                "new_password/${viewModel.token}/${viewModel.chosenUserId}"
//                            )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun UserRow(user: UserResetPassword, viewModel: ChooseUserViewModel) {
//    val selected = remember { mutableStateOf(false) }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(IntrinsicSize.Min),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        RadioButton(
//            selected = selected.value,
//            onClick = {
//                selected.value = !selected.value
//                viewModel.onUserChosen(user.id)
//            },
//            colors = RadioButtonDefaults.colors(
//                selectedColor = Blue,
//                unselectedColor = Gray
//            ),
//            modifier = Modifier.fillMaxHeight()
//        )
//        Column {
//            Text(
//                text = user.roleList.getOrNull(0)?.name ?: "",
//                style = MaterialTheme.typography.body1,
//                color = Blue
//            )
//            Text(
//                text = user.username,
//                style = MaterialTheme.typography.body1,
//                color = TextBlack
//            )
//            Text(
//                text = "",
//                style = MaterialTheme.typography.body1,
//                color = BorderGray
//            )
//        }
//    }
//}