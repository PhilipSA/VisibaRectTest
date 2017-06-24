package com.example.visiba.visibarectest

import java.io.Serializable
import java.util.Date
import java.util.UUID

class WallPost {
    var id: UUID = UUID.randomUUID()
    var textContent: String
    @Transient var leftImage: AppImage? = null
    @Transient var rightImage: AppImage? = null
    var postedDate: Date
    var leftImageId: UUID?
    var rightImageId: UUID?

    constructor(textContent: String, leftImage: AppImage?, rightImage: AppImage?) {
        this.textContent = textContent
        this.leftImage = leftImage
        this.rightImage = rightImage
        leftImageId = leftImage?.imageId
        rightImageId = rightImage?.imageId
        this.postedDate = Date()
    }
}
