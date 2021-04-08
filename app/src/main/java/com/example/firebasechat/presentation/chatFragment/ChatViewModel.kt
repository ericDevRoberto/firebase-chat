package com.example.firebasechat.presentation.chatFragment

import android.app.Activity
import com.example.firebasechat.R
import com.example.firebasechat.utils.ViewModelCore
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.SignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

private const val ANONYMOUS = "anonymous"

class ChatViewModel : ViewModelCore<ChatAction>() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var signInClient: GoogleSignInClient
    val dataBase = FirebaseDatabase.getInstance()
    private val user: FirebaseUser? = firebaseAuth.currentUser

    init {
        authentication()
    }

    private fun authentication() {

        if (firebaseAuth.currentUser == null)
            mutableLiveData.value = ChatAction.WithoutAuthentication
    }

    fun startSignInClient(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun getUserName(): String {

        return if (user != null)
            user.displayName
        else
            ANONYMOUS
    }

    fun getUserPhotoUrl(): String {

        return if (user != null && user.photoUrl != null)
            user.photoUrl.toString()
        else
            String()
    }

    fun signOut() {
        firebaseAuth.signOut()
        signInClient.signOut()
        mutableLiveData.value = ChatAction.SignOut
    }
}