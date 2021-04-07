package com.example.firebasechat.presentation.SignInFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_SIGN_IN = 9001
private const val TAG = "SignInFragment"

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: FragmentSignInBinding
    private lateinit var signInClient: GoogleSignInClient
    private val fireBaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        binding.signInButton.setOnClickListener { signIn() }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        signInClient = GoogleSignIn.getClient(requireActivity(), gso)

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign In failed", e)
            }
        }
    }

    private fun signIn() {
        val signInIntent = signInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle" + acct.id)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        fireBaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d(TAG, "signInWithCredential:success")
                findNavController().navigate(SignInFragmentDirections.actionSecondFragmentToFirstFragment())
            }
            .addOnFailureListener {
                Log.w(TAG, "signInWithCredential", it)
                Toast.makeText(activity, "authentication Failed.", Toast.LENGTH_LONG).show()
            }
    }
}