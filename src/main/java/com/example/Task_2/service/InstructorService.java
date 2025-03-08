package com.example.Task_2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Task_2.model.Instructor;
import com.example.Task_2.repository.InstructorRepository;

@Service
public class InstructorService {
    private InstructorRepository instructorRepository;
    
    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }
    
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
    
    public Optional<Instructor> getInstructorByID(Integer id) {
        return instructorRepository.findById(id);
    }
    
    public Instructor getInstructorByEmail(String email) {
        return instructorRepository.findInstructorByEmail(email);
    }
    
}
