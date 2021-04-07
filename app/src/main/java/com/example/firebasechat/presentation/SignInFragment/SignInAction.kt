package com.example.firebasechat.presentation.SignInFragment

sealed class SignInAction {
    object Success : SignInAction()
    object Fail : SignInAction()
}