package com.example.Task_2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Task_2.model.Instructor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer>{
   
    @Query(value = "SELECT * FROM instructor WHERE email = :email", nativeQuery = true)
    Instructor findInstructorByEmail(@Param("email") String email);
}
