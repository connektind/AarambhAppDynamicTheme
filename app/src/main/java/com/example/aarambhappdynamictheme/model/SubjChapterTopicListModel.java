package com.example.aarambhappdynamictheme.model;

public class SubjChapterTopicListModel {
    String chapterId;
    String chapterName;
    String chapterURLS;
    String courseId;
    String topicId;
    String classId;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    int black_transparent_back;
    public SubjChapterTopicListModel(){

    }

    public SubjChapterTopicListModel(String chapterId, String chapterName, String chapterURLS, String courseId, String topicId, String classId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate, int black_transparent_back) {
   this.chapterId=chapterId;
   this.chapterName=chapterName;
   this.chapterURLS=chapterURLS;
   this.courseId=courseId;
   this.topicId=topicId;
   this.classId=classId;
   this.statusId=statusId;
   this.createdById=createdById;
   this.modifiedById=modifiedById;
   this.creationDate=creationDate;
   this.modificationDate=modificationDate;
   this.black_transparent_back=black_transparent_back;
    }

    public int getBlack_transparent_back() {
        return black_transparent_back;
    }

    public void setBlack_transparent_back(int black_transparent_back) {
        this.black_transparent_back = black_transparent_back;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterURLS() {
        return chapterURLS;
    }

    public void setChapterURLS(String chapterURLS) {
        this.chapterURLS = chapterURLS;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}