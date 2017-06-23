package com.example.visiba.visibarectest.Repositories

import android.content.Context

import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository
import com.example.visiba.visibarectest.WallPost

class WallPostRepository(override var dbName: String = "wallposts") : BaseRepository<WallPost>() {
    fun SaveWallPostData(wallPost: WallPost) {
        saveObject(wallPost, wallPost.id.toString())
    }
}
