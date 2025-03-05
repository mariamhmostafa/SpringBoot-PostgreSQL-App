// package com.example.Task2.controller;

// import com.example.Task2.seed.DatabaseSeeder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class Seeder {
//     private final DatabaseSeeder databaseSeeder;

//     @Autowired
//     public Seeder(DatabaseSeeder databaseSeeder) {
//         this.databaseSeeder = databaseSeeder;
//     }

//     @GetMapping("/seed")
//     public String seed() {
//         databaseSeeder.seedDatabase();
//         return "Database seeded!!";
//     }
// }
