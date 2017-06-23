package com.example.visiba.visibarectest.Repositories

import com.example.visiba.visibarectest.AppImage
import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository

class AppImageRepository(override var dbName: String = "appimages") : BaseRepository<AppImage>() {
    fun saveAppImage(appImage: AppImage) {
        saveObject(appImage, appImage.imageId.toString())
    }
}
