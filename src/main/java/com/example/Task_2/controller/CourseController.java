package com.example.Task_2.controller;

import com.example.Task_2.model.Course;
import com.example.Task_2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    
    private final CourseService courseService;
    
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @GetMapping
    public List<Course> getCourses() {
        return courseService.getAllCourses();
    }
    
    @GetMapping("/{instructorId}")
    public List<Course> getCoursesByInstructorId(@PathVariable Integer instructorId) {
        return courseService.getCoursesByInstructorId(instructorId);
    }
    
    @PostMapping("/{courseId}/students/{studentId}")
    public String enrollStudent(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.enrollStudent(courseId, studentId);
        return "Student enrolled in course successfully.";
    }
    
    @PutMapping("/{courseId}/students/{studentId}")
    public String unenrollStudent(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.unenrollStudent(courseId, studentId);
        return "Student unenrolled from course successfully.";
    }
    
}
