package com.example.firebasechat.presentation.chatFragment

import android.app.Activity
import com.example.firebasechat.R
import com.example.firebasechat.model.FriendlyMessage
import com.example.firebasechat.utils.ViewModelCore
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChatViewModel : ViewModelCore<ChatAction>() {

    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var signInClient: GoogleSignInClient
    val dataBase = FirebaseDatabase.getInstance()
    val MESSAGES_CHILD = "messages"
    val ANONYMOUS = "anonymous"

    init {
        authentication()
    }

    private fun authentication() {

        if (firebaseAuth.currentUser == null)
            mutableLiveData.value = ChatAction.WithoutAuthentication
    }

    fun startSignInClient(activity : Activity){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(activity, gso)
    }



    fun getUserName(): String {
        val user = firebaseAuth.currentUser

        return if (user != null)
            user.displayName
        else
            ANONYMOUS
    }

    fun getUserPhotoUrl(): String {
        val user = firebaseAuth.currentUser

        return if (user != null && user.photoUrl != null)
            user.photoUrl.toString()
        else
            String()
    }
}