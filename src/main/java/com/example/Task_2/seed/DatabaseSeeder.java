 package com.example.Task_2.seed;

 import com.example.Task_2.model.Course;
 import com.example.Task_2.model.Instructor;
 import com.example.Task_2.model.Student;
 import com.example.Task_2.repository.CourseRepository;
 import com.example.Task_2.repository.InstructorRepository;
 import com.example.Task_2.repository.StudentRepository;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 public class DatabaseSeeder {

     @Autowired
     private InstructorRepository instructorRepository;

     @Autowired
     private CourseRepository courseRepository;

     @Autowired
     private StudentRepository studentRepository;

     @Transactional
     public void seedDatabase(){
         // Clear existing data (optional)
         studentRepository.deleteAll();
         courseRepository.deleteAll();
         instructorRepository.deleteAll();

         // 1. Create Instructors
         Instructor instructor1 = new Instructor("Alice Instructor", "alice.instructor@example.com");
         Instructor instructor2 = new Instructor("Bob Instructor", "bob.instructor@example.com");
         Instructor instructor3 = new Instructor("Charlie Instructor", "charlie.instructor@example.com");
         instructorRepository.save(instructor1);
         instructorRepository.save(instructor2);
         instructorRepository.save(instructor3);

         // 2. Create Courses and assign them to instructors
         Course course1 = new Course("Mathematics", "MATH101", 3, instructor1);
         Course course2 = new Course("Physics", "PHYS101", 4, instructor1);
         Course course3 = new Course("Chemistry", "CHEM101", 3, instructor1);
         Course course4 = new Course("History", "HIST101", 3, instructor2);
         Course course5 = new Course("Geography", "GEO101", 2, instructor2);
         Course course6 = new Course("Computer Science", "CS101", 4, instructor3);
         Course course7 = new Course("Literature", "LIT101", 3, instructor3);
         courseRepository.save(course1);
         courseRepository.save(course2);
         courseRepository.save(course3);
         courseRepository.save(course4);
         courseRepository.save(course5);
         courseRepository.save(course6);
         courseRepository.save(course7);

         // 3. Create Students
         Student student1 = new Student("John Doe", "john@example.com");
         Student student2 = new Student("Jane Doe", "jane@example.com");
         Student student3 = new Student("Jim Bean", "jim@example.com");
         Student student4 = new Student("Jill Smith", "jill@example.com");
         Student student5 = new Student("Mark Twain", "mark@example.com");
         Student student6 = new Student("Susan Collins", "susan@example.com");
         Student student7 = new Student("Peter Parker", "peter@example.com");
         Student student8 = new Student("Bruce Wayne", "bruce@example.com");
         studentRepository.save(student1);
         studentRepository.save(student2);
         studentRepository.save(student3);
         studentRepository.save(student4);
         studentRepository.save(student5);
         studentRepository.save(student6);
         studentRepository.save(student7);
         studentRepository.save(student8);

         // 4. Enroll Students in Courses

         // Course 1: Mathematics - enroll student1, student2, student3
         course1.getStudents().add(student1);
         course1.getStudents().add(student2);
         course1.getStudents().add(student3);
         student1.getCourses().add(course1);
         student2.getCourses().add(course1);
         student3.getCourses().add(course1);

         // Course 2: Physics - enroll student2, student3, student4
         course2.getStudents().add(student2);
         course2.getStudents().add(student3);
         course2.getStudents().add(student4);
         student2.getCourses().add(course2);
         student3.getCourses().add(course2);
         student4.getCourses().add(course2);

         // Course 3: Chemistry - enroll student1, student5
         course3.getStudents().add(student1);
         course3.getStudents().add(student5);
         student1.getCourses().add(course3);
         student5.getCourses().add(course3);

         // Course 4: History - enroll student3, student4, student6
         course4.getStudents().add(student3);
         course4.getStudents().add(student4);
         course4.getStudents().add(student6);
         student3.getCourses().add(course4);
         student4.getCourses().add(course4);
         student6.getCourses().add(course4);

         // Course 5: Geography - enroll student4, student7, student8
         course5.getStudents().add(student4);
         course5.getStudents().add(student7);
         course5.getStudents().add(student8);
         student4.getCourses().add(course5);
         student7.getCourses().add(course5);
         student8.getCourses().add(course5);

         // Course 6: Computer Science - enroll student1, student2, student7, student8
         course6.getStudents().add(student1);
         course6.getStudents().add(student2);
         course6.getStudents().add(student7);
         course6.getStudents().add(student8);
         student1.getCourses().add(course6);
         student2.getCourses().add(course6);
         student7.getCourses().add(course6);
         student8.getCourses().add(course6);

         // Course 7: Literature - enroll student5, student6, student7
         course7.getStudents().add(student5);
         course7.getStudents().add(student6);
         course7.getStudents().add(student7);
         student5.getCourses().add(course7);
         student6.getCourses().add(course7);
         student7.getCourses().add(course7);

         // Save the updated associations in courses and students
         courseRepository.save(course1);
         courseRepository.save(course2);
         courseRepository.save(course3);
         courseRepository.save(course4);
         courseRepository.save(course5);
         courseRepository.save(course6);
         courseRepository.save(course7);

         studentRepository.save(student1);
         studentRepository.save(student2);
         studentRepository.save(student3);
         studentRepository.save(student4);
         studentRepository.save(student5);
         studentRepository.save(student6);
         studentRepository.save(student7);
         studentRepository.save(student8);
     }
 }
