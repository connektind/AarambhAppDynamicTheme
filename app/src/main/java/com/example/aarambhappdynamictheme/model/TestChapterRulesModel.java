package com.example.aarambhappdynamictheme.model;

public class TestChapterRulesModel {
    String testMasterId;
    String classId;
    String courseId;
    String topicId;
    String chapterId;
    String testTitle;
    String fromDate;
    String toDate;
    String testDuration;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    public TestChapterRulesModel(String testMasterId, String classId, String courseId, String topicId, String chapterId, String testTitle, String fromDate, String toDate, String testDuration, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        this.testMasterId=testMasterId;
        this.classId=classId;
        this.courseId=courseId;
        this.topicId=topicId;
        this.chapterId=chapterId;
        this.testTitle=testTitle;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.testDuration=testDuration;
        this.statusId=statusId;
        this.createdById=createdById;
        this.modifiedById=modifiedById;
        this.creationDate=creationDate;
        this.modificationDate=modificationDate;
    }

    public String getTestMasterId() {
        return testMasterId;
    }

    public void setTestMasterId(String testMasterId) {
        this.testMasterId = testMasterId;
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

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
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

    public String getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(String testDuration) {
        this.testDuration = testDuration;
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
