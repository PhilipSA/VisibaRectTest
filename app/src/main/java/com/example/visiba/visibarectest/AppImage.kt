package com.example.visiba.visibarectest

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import java.util.Date
import java.util.UUID
import android.graphics.drawable.Drawable
import android.media.Image

class AppImage(var takenDate: Date = Date()) {
    val imageId: UUID = UUID.randomUUID()
    lateinit var image: ByteArray

    constructor(rawImage: Image) : this() {
        var buffer = rawImage.planes[0].buffer;
        image = ByteArray(buffer.remaining())
        buffer.get(image)
    }

    fun getDrawable(context: Context): Drawable {
        val bitmapImage = BitmapFactory.decodeByteArray(image, 0, image.size, null)
        return BitmapDrawable(context.resources, bitmapImage)
    }
}
