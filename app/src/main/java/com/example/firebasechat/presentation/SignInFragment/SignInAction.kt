package com.example.firebasechat.presentation.SignInFragment

sealed class SignInAction {
    object AuthenticationSuccess : SignInAction()
    object AuthenticationFail : SignInAction()
}