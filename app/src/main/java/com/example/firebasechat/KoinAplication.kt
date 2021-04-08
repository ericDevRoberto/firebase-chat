package com.example.firebasechat

import android.app.Application
import com.example.firebasechat.DI.firebaseViewModelModule
import com.example.firebasechat.DI.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinAplication :Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinAplication)

            modules(listOf(
                viewModelModules,
                firebaseViewModelModule
            ))
        }
    }
}