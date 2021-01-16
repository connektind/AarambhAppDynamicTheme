package com.example.aarambhappdynamictheme.model;

public class StudentDetailModel {
    String studentId;
    String studentName;
    String studentGender;
    String studentMobile;
    String studentUsername;
    String studentEmail;
    String studentAddress;
    String studentCity;
    String studentDOB;
    String studentDORegis;
    String studentPassword;
    String studentImage;
    String parentId;
    String classId;

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String parentName) {
        ParentName = parentName;
    }

    String statusId;
    String createdById;
    String modifiedById;
    String creationDate;
    String modificationDate;
    String ParentName;

    public StudentDetailModel(String studentId, String studentName, String studentGender, String studentMobile, String studentUsername, String studentCity, String studentDOB, String studentDORegis, String studentPassword, String studentImage, String parentId, String classId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGender = studentGender;
        this.studentMobile = studentMobile;
        this.studentUsername = studentUsername;

        this.studentCity = studentCity;
        this.studentDOB = studentDOB;
        this.studentDORegis = studentDORegis;
        this.studentPassword = studentPassword;
        this.studentImage = studentImage;
        this.parentId = parentId;
        this.classId = classId;
        this.statusId = statusId;
        this.createdById = createdById;
        this.creationDate = creationDate;
        this.modifiedById = modifiedById;
        this.modificationDate = modificationDate;
    }

    public StudentDetailModel(String studentId, String studentName, String studentGender, String studentMobile, String studentUsername, String studentDOB, String studentDORegis, String studentPassword, String studentImage, String parentId, String classId, String statusId, String createdById, String modifiedById, String creationDate, String modificationDate) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentGender = studentGender;
        this.studentMobile = studentMobile;
        this.studentUsername = studentUsername;
        this.studentDOB = studentDOB;
        this.studentDORegis = studentDORegis;
        this.studentPassword = studentPassword;
        this.studentImage = studentImage;
        this.parentId = parentId;
        this.classId = classId;
        this.statusId = statusId;
        this.createdById = createdById;
        this.creationDate = creationDate;
        this.modifiedById = modifiedById;
        this.modificationDate = modificationDate;

    }




    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentCity() {
        return studentCity;
    }

    public void setStudentCity(String studentCity) {
        this.studentCity = studentCity;
    }

    public String getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(String studentDOB) {
        this.studentDOB = studentDOB;
    }

    public String getStudentDORegis() {
        return studentDORegis;
    }

    public void setStudentDORegis(String studentDORegis) {
        this.studentDORegis = studentDORegis;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
