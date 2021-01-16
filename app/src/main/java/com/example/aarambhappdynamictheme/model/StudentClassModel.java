package com.example.aarambhappdynamictheme.model;

import java.io.Serializable;

public class StudentClassModel implements Serializable {
    String classId;
    String studentClass;

    public StudentClassModel(String classId, String studentClass) {
this.classId=classId;
this.studentClass=studentClass;

    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
