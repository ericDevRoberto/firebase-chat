package com.example.firebasechat.presentation.chatFragment.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebasechat.R
import com.example.firebasechat.model.FriendlyMessage
import com.google.firebase.storage.FirebaseStorage

private const val TAG = "MessageViewHolder"

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var messageTextView: TextView = itemView.findViewById(R.id.messageTextView) as TextView
    private var messageImageView: ImageView = itemView.findViewById(R.id.messageImageView) as ImageView

    fun bindMessage(friendlyMessage: FriendlyMessage) {

        if (friendlyMessage.text != null) {

            messageTextView.text = friendlyMessage.text
            messageTextView.visibility = TextView.VISIBLE
            messageImageView.visibility = ImageView.GONE

        } else if (friendlyMessage.imageUrl != null) {

            val imageUrl: String = friendlyMessage.imageUrl.toString()

            if (imageUrl.startsWith("gs://")) {

                val storageReference = FirebaseStorage.getInstance()
                    .getReference(imageUrl)

                storageReference.downloadUrl
                    .addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()

                        Glide.with(messageImageView.context)
                            .load(downloadUrl)
                            .into(messageImageView)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Getting download url was not successful.", e)
                    }
            } else {
                Glide.with(messageImageView.context)
                    .load(friendlyMessage.imageUrl)
                    .into(messageImageView)
            }
            messageImageView.visibility = ImageView.VISIBLE
            messageTextView.visibility = TextView.GONE
        }
    }
}