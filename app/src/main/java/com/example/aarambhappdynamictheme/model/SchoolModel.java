package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class SchoolModel implements Serializable {

    String SchoolId,SchoolName,SchoolAddress,SchoolPhone,SchoolBoard,
                            SchoolMail, SchoolLogo, YoutubeChannelId,
                            YoutubeChannelKey,
                            IncludeAarambh,Username,Password, FirebaseKey, FirebaseSecretKey;


    public SchoolModel(String schoolId, String schoolName, String schoolAddress, String schoolPhone, String schoolBoard, String schoolMail, String schoolLogo, String youtubeChannelId, String youtubeChannelKey, String includeAarambh, String username, String password, String firebaseKey, String firebaseSecretKey) {
        SchoolId = schoolId;
        SchoolName = schoolName;
        SchoolAddress = schoolAddress;
        SchoolPhone = schoolPhone;
        SchoolBoard = schoolBoard;
        SchoolMail = schoolMail;
        SchoolLogo = schoolLogo;
        YoutubeChannelId = youtubeChannelId;
        YoutubeChannelKey = youtubeChannelKey;
        IncludeAarambh = includeAarambh;
        Username = username;
        Password = password;
        FirebaseKey = firebaseKey;
        FirebaseSecretKey = firebaseSecretKey;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getSchoolAddress() {
        return SchoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        SchoolAddress = schoolAddress;
    }

    public String getSchoolPhone() {
        return SchoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        SchoolPhone = schoolPhone;
    }

    public String getSchoolBoard() {
        return SchoolBoard;
    }

    public void setSchoolBoard(String schoolBoard) {
        SchoolBoard = schoolBoard;
    }

    public String getSchoolMail() {
        return SchoolMail;
    }

    public void setSchoolMail(String schoolMail) {
        SchoolMail = schoolMail;
    }

    public String getSchoolLogo() {
        return SchoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        SchoolLogo = schoolLogo;
    }

    public String getYoutubeChannelId() {
        return YoutubeChannelId;
    }

    public void setYoutubeChannelId(String youtubeChannelId) {
        YoutubeChannelId = youtubeChannelId;
    }

    public String getYoutubeChannelKey() {
        return YoutubeChannelKey;
    }

    public void setYoutubeChannelKey(String youtubeChannelKey) {
        YoutubeChannelKey = youtubeChannelKey;
    }

    public String getIncludeAarambh() {
        return IncludeAarambh;
    }

    public void setIncludeAarambh(String includeAarambh) {
        IncludeAarambh = includeAarambh;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirebaseKey() {
        return FirebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        FirebaseKey = firebaseKey;
    }

    public String getFirebaseSecretKey() {
        return FirebaseSecretKey;
    }

    public void setFirebaseSecretKey(String firebaseSecretKey) {
        FirebaseSecretKey = firebaseSecretKey;
    }
}
