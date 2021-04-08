package com.example.firebasechat.presentation.signInFragment

import android.util.Log
import com.example.firebasechat.utils.ViewModelCore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG = "SignInViewModel"

class SignInViewModel : ViewModelCore<SignInAction>() {

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount, fireBaseAuth: FirebaseAuth) {
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
}