package com.example.Task_2.repository;

import java.util.List;

import com.example.Task_2.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO student_course (course_id, student_id) VALUES (:courseId, :studentId)", nativeQuery = true)
    void enrollStudent(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
    
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student_course WHERE course_id = :courseId AND student_id = :studentId", nativeQuery = true)
    void unenrollStudent(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
    
    List<Course> findByInstructorId(Integer instructorId);
}
