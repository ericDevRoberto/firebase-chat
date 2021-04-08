package com.example.firebasechat.DI

import com.example.firebasechat.repository.FirebaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseViewModelModule = module {
    single { FirebaseViewModel() }
}