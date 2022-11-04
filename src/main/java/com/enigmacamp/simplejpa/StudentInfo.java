package com.enigmacamp.simplejpa;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "M_STUDENT_INFO")
public class StudentInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_info_id")
    private String studentInfoId;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "religion")
    private String religion;

    public String getStudentInfoId() {
        return studentInfoId;
    }

    public void setStudentInfoId(String studentInfoId) {
        this.studentInfoId = studentInfoId;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentInfoId='" + studentInfoId + '\'' +
                ", hobby='" + hobby + '\'' +
                ", religion='" + religion + '\'' +
                '}';
    }
}
