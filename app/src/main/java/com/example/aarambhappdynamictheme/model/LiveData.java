package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class LiveData implements Serializable {

    String image,webninars;
    boolean live;

    public LiveData(String image, String webninars, boolean live) {
        this.image = image;
        this.webninars = webninars;
        this.live = live;
    }

    public LiveData(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebninars() {
        return webninars;
    }

    public void setWebninars(String webninars) {
        this.webninars = webninars;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }


}
