package com.example.visiba.visibarectest.Handlers;

import com.example.visiba.visibarectest.WallPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017-04-27.
 */

public class WallPostsHandler
{
    StorageHandler storageHandler;

    public WallPostsHandler(StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
    }

    public WallPost ConvertSerializableToWallPost(WallPost.SerializableWallPost serializableWallPost)
    {
        return new WallPost(serializableWallPost.id,
                serializableWallPost.textContent,
                storageHandler.loadImagesFromStorage(String.valueOf(serializableWallPost.leftImageId)),
                storageHandler.loadImagesFromStorage(String.valueOf(serializableWallPost.rightImageId)),
                serializableWallPost.postedDate);
    }
}
