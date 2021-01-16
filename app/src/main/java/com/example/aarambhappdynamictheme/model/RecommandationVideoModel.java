package com.example.aarambhappdynamictheme.model;

public class RecommandationVideoModel  {
    String video_back_image_link;
    int transprent_color;
    String video_link;
    public RecommandationVideoModel(String video_back_image_link, int transprent_color, String video_link) {
        this.video_back_image_link=video_back_image_link;
        this.transprent_color=transprent_color;
        this.video_link=video_link;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public int getTransprent_color() {
        return transprent_color;
    }

    public void setTransprent_color(int transprent_color) {
        this.transprent_color = transprent_color;
    }

    public String getVideo_back_image_link() {
        return video_back_image_link;
    }

    public void setVideo_back_image_link(String video_back_image_link) {
        this.video_back_image_link = video_back_image_link;
    }
}
