package com.video;

/**
 * Created by chang on 2017/5/8 0008.
 */

public class video {
    private String name;
    private int videoId;

    public video(String name, int videoId) {
        this.name = name;
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public int getVideoId() {
        return videoId;
    }
}
