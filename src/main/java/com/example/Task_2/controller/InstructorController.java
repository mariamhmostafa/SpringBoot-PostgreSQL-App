package com.example.Task_2.controller;

import com.example.Task_2.model.Instructor;
import com.example.Task_2.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorService instructorService;
    
    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }
    
    @GetMapping
    public List<Instructor> getInstructors() {
        return instructorService.getAllInstructors();
    }
    
    @GetMapping("/{email}")
    public Instructor getInstructorByEmail(@PathVariable String email) {
        return instructorService.getInstructorByEmail(email);
    }

}
