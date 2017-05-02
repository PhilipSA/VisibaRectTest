package com.example.visiba.visibarectest;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class WallPost
{
    public UUID id;
    public String textContent;
    public AppImage leftImage;
    public AppImage rightImage;
    public Date postedDate;

    public WallPost(UUID id, String textContent, AppImage leftImage, AppImage rightImage, Date postedDate) {
        this.id = id;
        this.textContent = textContent;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.postedDate = postedDate;
    }

    public WallPost(String textContent, AppImage leftImage, AppImage rightImage) {
        this.id = UUID.randomUUID();
        this.textContent = textContent;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.postedDate = new Date();
    }

    public static class SerializableWallPost implements Serializable {
        public UUID id;
        public String textContent;
        public UUID leftImageId;
        public UUID rightImageId;
        public Date postedDate;

        public SerializableWallPost(WallPost wallPost) {
            this.id = wallPost.id;
            this.textContent = wallPost.textContent;
            this.leftImageId = wallPost.leftImage != null ? wallPost.leftImage.imageId : null;
            this.rightImageId = wallPost.rightImage != null ? wallPost.rightImage.imageId : null;
            this.postedDate = wallPost.postedDate;
        }
    }
}
