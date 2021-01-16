package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class LiveViewModel implements Serializable {

    String  studentname,studentID,studentClass,studentbloodGroup,studentFatherName,studentPhNumber,studentMotherName;
    boolean livestatus;



    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentbloodGroup() {
        return studentbloodGroup;
    }

    public void setStudentbloodGroup(String studentbloodGroup) {
        this.studentbloodGroup = studentbloodGroup;
    }

    public String getStudentFatherName() {
        return studentFatherName;
    }

    public void setStudentFatherName(String studentFatherName) {
        this.studentFatherName = studentFatherName;
    }

    public String getStudentPhNumber() {
        return studentPhNumber;
    }

    public void setStudentPhNumber(String studentPhNumber) {
        this.studentPhNumber = studentPhNumber;
    }

    public String getStudentMotherName() {
        return studentMotherName;
    }

    public void setStudentMotherName(String studentMotherName) {
        this.studentMotherName = studentMotherName;
    }

    public boolean isLivestatus() {
        return livestatus;
    }

    public void setLivestatus(boolean livestatus) {
        this.livestatus = livestatus;
    }
}
