package com.example.firebasechat.repository

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebasechat.R
import com.example.firebasechat.presentation.chatFragment.ChatAction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class FirebaseViewModel : ViewModel() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val dataBase = FirebaseDatabase.getInstance()
    val user: FirebaseUser? = firebaseAuth.currentUser
    lateinit var signInClient: GoogleSignInClient

    private val _auth=  MutableLiveData<FirebaseAuth>()
    val auth: LiveData<FirebaseAuth> = _auth


    fun startSignInClient(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun autheticationOk(){
        _auth.value = firebaseAuth
    }

    fun signOut() {
        firebaseAuth.signOut()
        signInClient.signOut()

        _auth.value = firebaseAuth
    }
}