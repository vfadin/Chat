package com.vfadin.events.ui.profile

sealed interface ProfileEvent {
    object ChangeEmailClicked : ProfileEvent
    object ChangeNameClicked : ProfileEvent
    object ChangeNicknameClicked : ProfileEvent
    object LogoutClicked : ProfileEvent
}