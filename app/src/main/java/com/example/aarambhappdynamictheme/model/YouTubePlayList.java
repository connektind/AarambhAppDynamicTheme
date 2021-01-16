package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class YouTubePlayList implements Serializable {

    String playListId, playListTitle, playListURL;

    public YouTubePlayList(String playListId, String playListTitle, String playListURL) {
        this.playListId = playListId;
        this.playListTitle = playListTitle;
        this.playListURL = playListURL;
    }

    public String getPlayListId() {
        return playListId;
    }

    public void setPlayListId(String playListId) {
        this.playListId = playListId;
    }

    public String getPlayListTitle() {
        return playListTitle;
    }

    public void setPlayListTitle(String playListTitle) {
        this.playListTitle = playListTitle;
    }

    public String getPlayListURL() {
        return playListURL;
    }

    public void setPlayListURL(String playListURL) {
        this.playListURL = playListURL;
    }
}
