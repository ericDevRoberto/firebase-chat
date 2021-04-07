package com.example.firebasechat.presentation.ChatFragment

sealed class ChatAction {
    object Success : ChatAction()
    object Fail : ChatAction()

}