package com.vfadin.events.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.vfadin.events.ui.components.BottomNavBar
import com.vfadin.events.ui.components.ClickableBlueText
import com.vfadin.events.R
import com.vfadin.events.ui.components.FormattedTopAppBar
import com.vfadin.events.ui.components.WidthButton
import com.vfadin.events.ui.theme.font
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    val profileState = viewModel.profileState
    val intent = createFileUploadIntent()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.profileState = viewModel.profileState.copy(avatar = it.data?.data.toString())
        }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    ModalBottomSheetLayout(
        sheetContent = {
            AvatarBottomSheet {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    viewModel.profileState = viewModel.profileState.copy(avatar = it.toString())
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.systemBarsPadding(),
    ) {
        Scaffold(
            topBar = { AppTopBar() },
            bottomBar = { BottomNavBar(navController) }
        ) { padding ->
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                item {
                    GlideImage(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(84.dp),
                        imageModel = profileState.avatar,
//                    imageModel = "",
                        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                        failure = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_account_circle),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(64.dp),
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Gray)
                            )
                        }
                    )
                }
                item {
                    Text(
                        text = "${profileState.firstName} ${profileState.lastName}",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                item {
                    Divider()
                }
                item {
                    EditAvatarRow(onClick = {
                        coroutineScope.launch {
                            modalBottomSheetState.show()
                        }
//                        launcher.launch(intent)
                    })
                }
                item {
                    Divider()
                }
                item {
                    EditUserField(
                        title = profileState.email,
                        changeTitle = "Tap to change email",
                        onClick = { viewModel.onEvent(ProfileEvent.ChangeEmailClicked) })
                    EditUserField(
                        title = profileState.nickname,
                        changeTitle = "Tap to change nickname",
                        onClick = { viewModel.onEvent(ProfileEvent.ChangeNicknameClicked) })
                    EditUserField(
                        title = "${profileState.firstName} ${profileState.lastName}",
                        changeTitle = "Tap to change name",
                        onClick = { viewModel.onEvent(ProfileEvent.ChangeNameClicked) })
                }
                item {
                    WidthButton(
                        modifier = Modifier.padding(top = 16.dp),
                        label = "Log out",
                        onClick = { viewModel.onEvent(ProfileEvent.LogoutClicked) },
                    )
                }
            }
        }
    }
}

@Composable
fun EditAvatarRow(
    onClick: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                onClick()
            }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_photo),
            contentDescription = null,
            tint = colorResource(R.color.blue),
            modifier = Modifier
                .padding(end = 16.dp)
                .size(20.dp)
        )
        Text(
            text = "Edit Profile Photo",
            style = TextStyle(
                fontFamily = font,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = colorResource(R.color.blue)
            )
        )
    }
}

@Composable
fun EditUserField(
    title: String,
    changeTitle: String,
    onClick: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }) {
        Text(
            text = title,
            modifier = Modifier.padding(top = 16.dp, bottom = 2.dp),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = changeTitle,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Divider()
    }
}


fun createFileUploadIntent(): Intent {
    val mimeTypes = arrayOf(
        "image/*"
    )
    return Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "*/*"
        putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
    }

}

@Composable
fun AvatarBottomSheet(
    onFilePicked: (Uri) -> Unit,
) {
    Column {
        Box(Modifier.padding(top = 16.dp, bottom = 16.dp)) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = size.width * 0.45f, y = 0f),
                    end = Offset(x = size.width * 0.55f, y = 0f),
                    strokeWidth = 5f
                )
            }
        }
        ImagePicker(onFilePicked = { onFilePicked(it) })
    }
}

@Composable
private fun AppTopBar() {
    FormattedTopAppBar(title = "Профиль")
}