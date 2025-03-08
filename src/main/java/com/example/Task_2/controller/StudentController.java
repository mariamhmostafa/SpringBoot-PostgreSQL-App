package com.example.Task_2.controller;

import com.example.Task_2.model.Student;
import com.example.Task_2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }
    
    @GetMapping("/{studentId}")
    public Optional<Student> getStudentById(@PathVariable int studentId) {
        return studentService.getStudentById(studentId);
    }
    
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
    
    @PutMapping("/{studentId}")
    public Student updateStudent(@PathVariable int studentId, @RequestBody Student student) {
        return studentService.updateStudent(studentId, student);
    }
    
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable int studentId) {
        studentService.deleteStudent(studentId);
    }
    
    @GetMapping("/course/{courseId}")
    public List<Student> getStudentsByCourseId(@PathVariable int courseId) {
        return studentService.getStudentsByCourseId(courseId);
    }
    
    
}
