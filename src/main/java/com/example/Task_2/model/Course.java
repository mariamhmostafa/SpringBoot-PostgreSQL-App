package com.example.Task_2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String code;
    private int credit;
    
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();;
    
    @ManyToOne
    @JoinColumn(name = "instructor_id",referencedColumnName = "id")
    private Instructor instructor;
    
    public Course(String name, String code, int credit, List<Student> students, Instructor instructor) {
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.students = students;
        this.instructor = instructor;
    }
    
    public Course(String name, String code, int credit, Instructor instructor) {
        this.name = name;
        this.code = code;
        this.credit = credit;
        this.instructor = instructor;
    }
    
    public Course() {
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public int getCredit() {
        return credit;
    }
    
    public void setCredit(int credit) {
        this.credit = credit;
    }
    
    public List<Student> getStudents() {
        return students;
    }
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public Instructor getInstructor() {
        return instructor;
    }
    
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
