package com.example.firebasechat.presentation.chatFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.firebasechat.R
import com.example.firebasechat.model.FriendlyMessage
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class FirebaseAdapter( dataBase : FirebaseDatabase) {

    companion object {

        inline fun <reified E> firebaseRecyclerChat(dataBase : FirebaseDatabase, childName: String): FirebaseRecyclerOptions<E> {

            val messagesRef = dataBase.reference.child(childName)

            return FirebaseRecyclerOptions.Builder<E>()
                .setQuery(messagesRef, E::class.java)
                .build()
        }

        fun firebaseAdapter(options: FirebaseRecyclerOptions<FriendlyMessage>, progressBar: ProgressBar? = null) =
            object : FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(options) {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): MessageViewHolder {
                    val inflater = LayoutInflater.from(parent.context)

                    return MessageViewHolder(
                        inflater.inflate(R.layout.item_message, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: MessageViewHolder,
                    position: Int,
                    model: FriendlyMessage
                ) {

                    if (progressBar != null)
                        progressBar.visibility = ProgressBar.INVISIBLE

                    holder.bindMessage(model)
                }
            }

    }
}