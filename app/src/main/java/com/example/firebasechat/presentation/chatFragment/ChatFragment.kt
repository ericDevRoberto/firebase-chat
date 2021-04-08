package com.example.firebasechat.presentation.chatFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MESSAGES_CHILD = "messages"

class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModel()
    private lateinit var binding: FragmentChatBinding
    private lateinit var firebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        viewModel.startSignInClient(requireActivity())

        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer { action ->
            when (action) {
                is ChatAction.WithoutAuthentication -> goToSignIn()
                is ChatAction.SignOut -> goToSignIn()
            }
        })

        val options = FirebaseAdapter.firebaseRecyclerChat<FriendlyMessage>(
            viewModel.dataBase,
            MESSAGES_CHILD
        )

        // Put "binding" in a "with"?
        firebaseAdapter = FirebaseAdapter.firebaseAdapter(options, binding.progressBar)

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

            viewModel.dataBase.reference.child(MESSAGES_CHILD).push().setValue(friendlyMessage)
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

    private fun goToSignIn() {
        findNavController().navigate(ChatFragmentDirections.actionFirstFragmentToSecondFragment())
    }


}