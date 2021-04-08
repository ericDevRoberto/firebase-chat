package com.example.firebasechat.presentation.chatFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasechat.R
import com.example.firebasechat.databinding.FragmentChatBinding
import com.example.firebasechat.model.FriendlyMessage
import com.example.firebasechat.presentation.chatFragment.adapter.FirebaseAdapter
import com.example.firebasechat.presentation.chatFragment.adapter.MessageViewHolder
import com.example.firebasechat.utils.MyButtonObserver
import com.example.firebasechat.utils.MyScrollToBottomObserver
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel
private const val MESSAGES_CHILD = "messages"
private const val ANONYMOUS = "anonymous"
private const val LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif"
private const val REQUEST_IMAGE = 2

class ChatFragment : Fragment() {

    private val viewModel : ChatViewModel by viewModel()
    private lateinit var binding : FragmentChatBinding
    private lateinit var firebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>

    val dataBase = FirebaseDatabase.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        viewModel.startSignInClient(requireActivity())

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {action ->
            when (action){
                is ChatAction.WithoutAuthentication -> goToSignIn()
            }
        })

        val options = FirebaseAdapter.firebaseRecyclerChat<FriendlyMessage>(viewModel.dataBase, MESSAGES_CHILD)

        firebaseAdapter = FirebaseAdapter.firebaseAdapter(options, binding.progressBar)
/
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.messagesRecycleView.layoutManager = linearLayoutManager
        binding.messagesRecycleView.adapter = firebaseAdapter

        firebaseAdapter.registerAdapterDataObserver(
            MyScrollToBottomObserver(
                aRecycler = binding.messagesRecycleView,
                aAdapter = firebaseAdapter,
                aManager = linearLayoutManager
            )
        )

        binding.messageEditText.addTextChangedListener(MyButtonObserver(binding.sendButton))

        binding.sendButton.setOnClickListener() {
            val friendlyMessage = FriendlyMessage(
                text = binding.messageEditText.text.toString(),
                name = viewModel.getUserName(),
                photoUrl = viewModel.getUserPhotoUrl(),
                imageUrl = null
            )

            dataBase.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
            binding.messageEditText.setText("")
        }

        return binding.root
    }

    override fun onPause() {
        firebaseAdapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        firebaseAdapter.startListening()
    }

    private fun goToSignIn(){
        findNavController().navigate(ChatFragmentDirections.actionFirstFragmentToSecondFragment())
    }
}