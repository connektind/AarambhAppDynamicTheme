package com.example.aarambhappdynamictheme.model;

public class CourseModel {

    String CourseId,CourseName,CourseDescription, CourseOtherDetails,CourseImage,ClassId,SchoolId,StatusId,
            CreatedById,ModifiedById,CreationDate,ModificationDate;


    public CourseModel(String courseId, String courseName, String courseDescription, String courseOtherDetails, String courseImage, String classId, String schoolId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        CourseId = courseId;
        CourseName = courseName;
        CourseDescription = courseDescription;
        CourseOtherDetails = courseOtherDetails;
        CourseImage = courseImage;
        ClassId = classId;
        SchoolId = schoolId;
        StatusId = statusId;
        CreatedById = createdById;
        ModifiedById = modifiedById;
        CreationDate = creationDate;
        ModificationDate = modificationDate;
    }


    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        CourseDescription = courseDescription;
    }

    public String getCourseOtherDetails() {
        return CourseOtherDetails;
    }

    public void setCourseOtherDetails(String courseOtherDetails) {
        CourseOtherDetails = courseOtherDetails;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
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
