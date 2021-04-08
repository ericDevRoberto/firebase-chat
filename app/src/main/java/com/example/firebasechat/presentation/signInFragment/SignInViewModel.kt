package com.example.firebasechat.presentation.signInFragment

import android.app.Activity
import android.util.Log
import com.example.firebasechat.R
import com.example.firebasechat.utils.ViewModelCore
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG = "SignInViewModel"

class SignInViewModel : ViewModelCore<SignInAction>() {

    lateinit var signInClient: GoogleSignInClient
    private val fireBaseAuth = FirebaseAuth.getInstance()

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle" + acct.id)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        fireBaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d(TAG, "signInWithCredential:success")
               mutableLiveData.value = SignInAction.AuthenticationSuccess
            }
            .addOnFailureListener {
                Log.w(TAG, "signInWithCredential", it)
                mutableLiveData.value = SignInAction.AuthenticationFail
            }
    }

    fun startSignInClient(activity: Activity) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.resources.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(activity, gso)
    }



}