package com.example.aarambhappdynamictheme.model;

public class ChapterModel {
    String  ChapterId,ChapterName,ChapterURLS,CourseId,TopicId,
            ClassId,SchoolId,StatusId,CreatedById,ModifiedById,CreationDate,ModificationDate;

    public ChapterModel(String chapterId, String chapterName, String chapterURLS, String courseId, String topicId, String classId, String schoolId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        ChapterId = chapterId;
        ChapterName = chapterName;
        ChapterURLS = chapterURLS;
        CourseId = courseId;
        TopicId = topicId;
        ClassId = classId;
        SchoolId = schoolId;
        StatusId = statusId;
        CreatedById = createdById;
        ModifiedById = modifiedById;
        CreationDate = creationDate;
        ModificationDate = modificationDate;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public String getChapterURLS() {
        return ChapterURLS;
    }

    public void setChapterURLS(String chapterURLS) {
        ChapterURLS = chapterURLS;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getTopicId() {
        return TopicId;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
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
