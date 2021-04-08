package com.example.firebasechat.presentation.signInFragment

sealed class SignInAction {
    object AuthenticationSuccess : SignInAction()
    object AuthenticationFail : SignInAction()
}