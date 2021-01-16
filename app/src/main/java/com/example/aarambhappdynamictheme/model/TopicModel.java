package com.example.aarambhappdynamictheme.model;

public class TopicModel {
    String TopicId, TopicName,TopicImage,TopicOtherDetail,ClassId,
            SchoolId, CourseId,StatusId,CreatedById,
            ModifiedById,CreationDate, ModificationDate;

    public TopicModel(String topicId, String topicName, String topicImage, String topicOtherDetail, String classId, String schoolId, String courseId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        TopicId = topicId;
        TopicName = topicName;
        TopicImage = topicImage;
        TopicOtherDetail = topicOtherDetail;
        ClassId = classId;
        SchoolId = schoolId;
        CourseId = courseId;
        StatusId = statusId;
        CreatedById = createdById;
        ModifiedById = modifiedById;
        CreationDate = creationDate;
        ModificationDate = modificationDate;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getTopicImage() {
        return TopicImage;
    }

    public void setTopicImage(String topicImage) {
        TopicImage = topicImage;
    }

    public String getTopicOtherDetail() {
        return TopicOtherDetail;
    }

    public void setTopicOtherDetail(String topicOtherDetail) {
        TopicOtherDetail = topicOtherDetail;
    }

    public String getClassId() {
        return ClassId;
    }

    public void setClassId(String classId) {
        ClassId = classId;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public void setCreatedById(String createdById) {
        CreatedById = createdById;
    }

    public String getModifiedById() {
        return ModifiedById;
    }

    public void setModifiedById(String modifiedById) {
        ModifiedById = modifiedById;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(String modificationDate) {
        ModificationDate = modificationDate;
    }
}
