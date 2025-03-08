package com.example.Task_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Task_2.model.Student;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    @Modifying
    @Transactional
    @Query(value = "SELECT s.* FROM student s " +
            "JOIN student_course sc ON s.id = sc.student_id " +
            "WHERE sc.course_id = :courseId", nativeQuery = true)
    List<Student> findStudentsByCourseId(@Param("courseId") Integer courseId);
    
}
