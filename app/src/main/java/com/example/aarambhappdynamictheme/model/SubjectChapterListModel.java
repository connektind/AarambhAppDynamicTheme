package com.example.aarambhappdynamictheme.model;

public class SubjectChapterListModel {
    String topicId;
    String topicName;
    String topicOtherDetail;
    String classId;
    String courseId;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
//    public SubjectChapterListModel(String chapter_name, SubjChapterTopicListAdapter subjChapterTopicListAdapter) {
//   this.chapter_name=chapter_name;
//   this.subjChapterTopicListAdapter=subjChapterTopicListAdapter;
//
//    }
    public SubjectChapterListModel(){

    }

    public SubjectChapterListModel(String topicId, String topicName, String topicOtherDetail, String classId, String courseId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
   this.topicId=topicId;
   this.topicName=topicName;
   this.topicOtherDetail=topicOtherDetail;
   this.classId=classId;
   this.courseId=courseId;
   this.statusId=statusId;
   this.createdById=createdById;
   this.creationDate=creationDate;
   this.modifiedById=modifiedById;
   this.modificationDate=modificationDate;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicOtherDetail() {
        return topicOtherDetail;
    }

    public void setTopicOtherDetail(String topicOtherDetail) {
        this.topicOtherDetail = topicOtherDetail;
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
