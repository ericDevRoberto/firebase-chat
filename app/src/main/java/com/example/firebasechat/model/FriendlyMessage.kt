package com.example.firebasechat.model

class FriendlyMessage {
    var text: String? = null
    var name: String? = null
    private var photoUrl: String? = null
    var imageUrl: String? = null

    constructor(){}
    constructor(
        text: String?,
        name: String?,
        photoUrl: String?,
        imageUrl: String?
    ) {
        this.text = text
        this.name = name
        this.photoUrl = photoUrl
        this.imageUrl = imageUrl
    }

}