package com.example.firebasechat.presentation.ChatFragment

sealed class ChatAction {
    object WithoutAuthentication : ChatAction()
}