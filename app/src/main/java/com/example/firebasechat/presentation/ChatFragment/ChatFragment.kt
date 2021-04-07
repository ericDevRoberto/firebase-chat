package com.example.firebasechat.presentation.ChatFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.firebasechat.R
import com.example.firebasechat.databinding.FragmentChatBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment() {

    private val viewModel : ChatViewModel by viewModel()
    private lateinit var binding : FragmentChatBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {action ->
            when (action){
                is ChatAction.WithoutAuthentication -> goToSignIn()
            }
        })

        return binding.root
    }

    private fun goToSignIn(){
        findNavController().navigate(ChatFragmentDirections.actionFirstFragmentToSecondFragment())
    }
}