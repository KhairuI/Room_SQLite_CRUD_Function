package com.example.sqlite_room_crud;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int studentId;

    private String studentName;
    private String studentMark;
    private String studentGender;

    public Student(String studentName, String studentMark, String studentGender) {
        this.studentName = studentName;
        this.studentMark = studentMark;
        this.studentGender = studentGender;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(String studentMark) {
        this.studentMark = studentMark;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }
}
