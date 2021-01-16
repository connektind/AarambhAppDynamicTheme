package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;
import java.util.ArrayList;

public class YouTubeTitle implements Serializable {
    ArrayList<String> topic;
    ArrayList<String> subject;
    ArrayList<String> chapter;
    ArrayList<String> videoId;
    static int background;
    ArrayList<String> videoTitle;

    public YouTubeTitle(int subj_back_bg) {
        this.background=subj_back_bg;
    }

    public YouTubeTitle(){}

//    public YouTubeTitle(ArrayList<String> subject,String chapter, String topic,int subj_back_bg){
//        this.subject = subject;
//        this.chapter = chapter;
//        this.topic = topic;
//        this.background=subj_back_bg;
//    }


    public ArrayList<String> getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(ArrayList<String> videoTitle) {
        this.videoTitle = videoTitle;
    }

    public static int getBackground() {
        return background;
    }

    public static void setBackground(int background) {
        background = background;
    }

    public ArrayList<String> getSubject() {
        return subject;
    }

    public void setSubject(ArrayList<String> subject) {
        this.subject = subject;
    }

    public ArrayList<String> getChapter() {
        return chapter;
    }

    public void setChapter(ArrayList<String> chapter) {
        this.chapter = chapter;
    }

    public ArrayList<String> getTopic() {
        return topic;
    }

    public void setTopic(ArrayList<String> topic) {
        this.topic = topic;
    }

    public ArrayList<String> getVideoId() {
        return videoId;
    }

    public void setVideoId(ArrayList<String> videoId) {
        this.videoId = videoId;
    }
}
