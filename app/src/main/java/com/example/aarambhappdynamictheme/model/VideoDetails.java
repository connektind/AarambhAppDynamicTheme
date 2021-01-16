package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class VideoDetails implements Serializable {
    String VideoName;
    String VideoDesc;
    String URL;
    String VideoId;

    public VideoDetails(){}

    public VideoDetails(String videoName, String videoDesc, String URL, String videoId) {
        VideoName = videoName;
        VideoDesc = videoDesc;
        this.URL = URL;
        VideoId = videoId;
    }

    public void setVideoName(String VideoName){
        this.VideoName=VideoName;
    }
    public String getVideoName(){
        return VideoName;
    }
    public void setVideoDesc(String VideoDesc){
        this.VideoDesc=VideoDesc;
    }
    public String getVideoDesc(){
        return VideoDesc;
    }
    public void setURL(String URL){
        this.URL=URL;
    }
    public String getURL(){
        return URL;
    }
    public void setVideoId(String VideoId){
        this.VideoId=VideoId;
    }
    public String getVideoId(){
        return VideoId;
    }
}

