package com.example.Task_2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Task_2.model.Course;
import com.example.Task_2.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseByID(Integer id) {
        return courseRepository.findById(id);
    }
    
    //TODO: find a way to implement this method
    public List<Course> getCoursesByInstructorId(Integer instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    public void enrollStudent(Integer courseId, Integer studentId) {
        courseRepository.enrollStudent(courseId, studentId);
    }
    
    public void unenrollStudent(Integer courseId, Integer studentId) {
        courseRepository.unenrollStudent(courseId, studentId);
    }
    
    
}
