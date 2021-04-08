package com.example.firebasechat.DI

import com.example.firebasechat.presentation.chatFragment.ChatViewModel
import com.example.firebasechat.presentation.signInFragment.SignInViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module{

    viewModel {
        ChatViewModel()
    }

    viewModel {
        SignInViewModel()
    }
}