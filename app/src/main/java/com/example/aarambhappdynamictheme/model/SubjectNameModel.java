package com.example.aarambhappdynamictheme.model;

public class SubjectNameModel {
    String subj_icon;
    String subject_name;
    int subj_back_bg;



    String courseId;
    String courseName;
    String courseDescription;
    String courseOtherDetails;
    String courseImage;
    String classId;
    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;




    public SubjectNameModel(String subj_icon, String subject_name, int subj_back_bg) {
    this.subj_icon=subj_icon;
    this.subject_name=subject_name;
        this.subj_back_bg=subj_back_bg;
    }

    public SubjectNameModel(String courseId, String courseName, String courseDescription, String courseOtherDetails, String courseImage, String classId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate,int subject_back) {
   this.courseId=courseId;
   this.courseName=courseName;
   this.courseDescription=courseDescription;
   this.courseOtherDetails=courseOtherDetails;
   this.courseImage=courseImage;
   this.classId=classId;
   this.statusId=statusId;
   this.createdById=createdById;
   this.modifiedById=modifiedById;
   this.creationDate=creationDate;
   this.modificationDate=modificationDate;
   this.subj_back_bg=subject_back;
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseOtherDetails() {
        return courseOtherDetails;
    }

    public void setCourseOtherDetails(String courseOtherDetails) {
        this.courseOtherDetails = courseOtherDetails;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
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

    public int getSubj_back_bg() {
        return subj_back_bg;
    }

    public void setSubj_back_bg(int subj_back_bg) {
        this.subj_back_bg = subj_back_bg;
    }

    public String getSubj_icon() {
        return subj_icon;
    }

    public void setSubj_icon(String subj_icon) {
        this.subj_icon = subj_icon;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
