package com.example.aarambhappdynamictheme.model;

public class PraticesChapterListModel {
    String practiceMasterId;
    String classId;
    String courseId;
    String topicId;
    String chapterId;
    String practiceTitle;
    String fromDate;
    String toDate;
    String practiceDuration;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    public PraticesChapterListModel(String practiceMasterId, String classId, String courseId, String topicId, String chapterId, String practiceTitle, String fromDate, String toDate, String practiceDuration, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
  this.practiceMasterId=practiceMasterId;
  this.classId=classId;
  this.courseId=courseId;
  this.topicId=topicId;
  this.chapterId=chapterId;
  this.practiceTitle=practiceTitle;
  this.fromDate=fromDate;
  this.toDate=toDate;
  this.practiceDuration=practiceDuration;
  this.statusId=statusId;
  this.createdById=createdById;
  this.modifiedById=modifiedById;
  this.creationDate=creationDate;
  this.modificationDate=modificationDate;

    }

    public String getPracticeMasterId() {
        return practiceMasterId;
    }

    public void setPracticeMasterId(String practiceMasterId) {
        this.practiceMasterId = practiceMasterId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getPracticeTitle() {
        return practiceTitle;
    }

    public void setPracticeTitle(String practiceTitle) {
        this.practiceTitle = practiceTitle;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPracticeDuration() {
        return practiceDuration;
    }

    public void setPracticeDuration(String practiceDuration) {
        this.practiceDuration = practiceDuration;
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
