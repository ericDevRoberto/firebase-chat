package com.example.firebasechat.presentation.ChatFragment

import com.example.firebasechat.utils.ViewModelCore
import com.google.firebase.auth.FirebaseAuth

class ChatViewModel : ViewModelCore<ChatAction>() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        authentication()
    }

    private fun authentication() {

        if (firebaseAuth.currentUser == null)
            mutableLiveData.value = ChatAction.WithoutAuthentication
    }
}