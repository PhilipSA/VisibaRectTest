package com.example.visiba.visibarectest

import android.content.Context
import android.graphics.BitmapFactory
import java.util.Date
import java.util.UUID
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class AppImage(var imageId: UUID, var image: ByteArray, var takenDate: Date = Date()) {
    fun getDrawable(context: Context): Drawable {
        val bitmapImage = BitmapFactory.decodeByteArray(image, 0, image.size, null);
        return BitmapDrawable(context.resources, bitmapImage)
    }
}
