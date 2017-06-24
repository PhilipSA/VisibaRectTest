package com.example.visiba.visibarectest.Repositories

import android.content.Context

import com.example.visiba.visibarectest.Repositories.Abstractions.BaseRepository
import com.example.visiba.visibarectest.WallPost

class WallPostRepository(override var dbName: String = "wallposts", val appImageRepository: AppImageRepository = AppImageRepository()) : BaseRepository<WallPost>() {
    fun SaveWallPostData(wallPost: WallPost) {
        saveObject(wallPost, wallPost.id.toString())
    }

    override fun loadItem(itemKey: String): WallPost? {
        val wallPost = super.loadItem(itemKey)
        wallPost?.rightImage = appImageRepository.loadItem(wallPost?.rightImageId.toString())
        wallPost?.leftImage = appImageRepository.loadItem(wallPost?.leftImageId.toString())
        return wallPost
    }

    override fun loadAllItems(): MutableList<WallPost> {
        val wallPosts = super.loadAllItems()
        wallPosts.forEach {
            it?.rightImage = appImageRepository.loadItem(it?.rightImageId.toString())
            it?.leftImage = appImageRepository.loadItem(it?.leftImageId.toString())
        }
        return wallPosts
    }
}
