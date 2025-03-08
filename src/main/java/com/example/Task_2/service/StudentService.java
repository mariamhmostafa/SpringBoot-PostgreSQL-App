package com.example.Task_2.service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Task_2.model.Student;
import com.example.Task_2.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }
    
    public List<Student> getStudentsByCourseId(Integer courseId) {
        return studentRepository.findStudentsByCourseId(courseId);
    }
    
    @Transactional
    public Student updateStudent(Integer studentId, Student updatedStudent) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setCourses(updatedStudent.getCourses());
        
        return studentRepository.save(student);
    }
    
    
}
