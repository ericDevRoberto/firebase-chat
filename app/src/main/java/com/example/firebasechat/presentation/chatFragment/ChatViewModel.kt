package com.example.firebasechat.presentation.chatFragment

import androidx.lifecycle.LiveData
import com.example.firebasechat.utils.ViewModelCore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val ANONYMOUS = "anonymous"

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class ChatViewModel : ViewModelCore<ChatAction>() {

    fun authentication(firebaseAuth: FirebaseAuth) {

        if (firebaseAuth?.currentUser == null)
            notAuthenticated()
    }

    fun notAuthenticated(){
        mutableLiveData.value = ChatAction.WithoutAuthentication
    }

    fun getUserName(user: FirebaseUser?): String {

        return if (user != null)
            user.displayName
        else
            ANONYMOUS
    }

    fun getUserPhotoUrl(user: FirebaseUser?): String {

        return if (user != null && user.photoUrl != null)
            user.photoUrl.toString()
        else
            String()
    }
}