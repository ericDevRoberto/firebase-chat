package com.example.firebasechat.presentation.chatFragment

sealed class ChatAction {
    object WithoutAuthentication : ChatAction()
    object SignOut : ChatAction()
}