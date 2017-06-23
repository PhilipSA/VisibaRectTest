package com.example.visiba.visibarectest

import java.io.Serializable
import java.util.Date
import java.util.UUID

class WallPost {
    var id: UUID
    var textContent: String
    var leftImage: AppImage? = null
    var rightImage: AppImage? = null
    var postedDate: Date

    constructor(id: UUID, textContent: String, leftImage: AppImage, rightImage: AppImage, postedDate: Date) {
        this.id = id
        this.textContent = textContent
        this.leftImage = leftImage
        this.rightImage = rightImage
        this.postedDate = postedDate
    }

    constructor(textContent: String, leftImage: AppImage, rightImage: AppImage) {
        this.id = UUID.randomUUID()
        this.textContent = textContent
        this.leftImage = leftImage
        this.rightImage = rightImage
        this.postedDate = Date()
    }
}
