package com.example.firebasechat.presentation.signInFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.databinding.FragmentSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_SIGN_IN = 9001
private const val TAG = "SignInFragment"

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)

        viewModel.startSignInClient(requireActivity())

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is SignInAction.AuthenticationSuccess -> goToChatFragment()
                is SignInAction.AuthenticationFail -> authenticationFail()
            }
        })

        binding.signInButton.setOnClickListener { signIn() }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { viewModel.firebaseAuthWithGoogle(it) }

            } catch (e: ApiException) {
                Log.w(TAG, "Google sign In failed", e)
            }
        }
    }

    private fun signIn() {
        val signInIntent = viewModel.signInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun goToChatFragment() {
        findNavController().navigate(SignInFragmentDirections.actionSecondFragmentToFirstFragment())
    }

    private fun authenticationFail() {
        Toast.makeText(activity, "authentication Failed.", Toast.LENGTH_LONG).show()
    }
}