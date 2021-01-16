package com.example.aarambhappdynamictheme.model;

public class ThemeModel {
    String class_id;
    String baseImage;
    String transpent_color;
    String base_color_one;
    String base_color_two;
    public ThemeModel(String class_id, String baseImage, String transpent_color, String base_color_one, String base_color_two) {
     this.class_id=class_id;
     this.baseImage=baseImage;
     this.transpent_color=transpent_color;
     this.base_color_one=base_color_one;
     this.base_color_two=base_color_two;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getBaseImage() {
        return baseImage;
    }

    public void setBaseImage(String baseImage) {
        this.baseImage = baseImage;
    }

    public String getTranspent_color() {
        return transpent_color;
    }

    public void setTranspent_color(String transpent_color) {
        this.transpent_color = transpent_color;
    }

    public String getBase_color_one() {
        return base_color_one;
    }

    public void setBase_color_one(String base_color_one) {
        this.base_color_one = base_color_one;
    }

    public String getBase_color_two() {
        return base_color_two;
    }

    public void setBase_color_two(String base_color_two) {
        this.base_color_two = base_color_two;
    }
}
