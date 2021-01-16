package com.example.aarambhappdynamictheme.model;

public class TopicvideoListModel {
    String video_image_link;
    String video_name;
    String video_time;
    String video_link;
    public TopicvideoListModel(String video_image_link, String video_name, String video_time, String video_link) {
        this.video_image_link=video_image_link;
        this.video_name=video_name;
        this.video_time=video_time;
        this.video_link=video_link;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public String getVideo_image_link() {
        return video_image_link;
    }

    public void setVideo_image_link(String video_image_link) {
        this.video_image_link = video_image_link;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }
}
