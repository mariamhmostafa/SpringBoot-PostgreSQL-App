package com.example.Task_2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc

@Sql(statements = {
		// Drop tables if they exist (order matters due to FK constraints)
		"DROP TABLE IF EXISTS student_course;",
		"DROP TABLE IF EXISTS course;",
		"DROP TABLE IF EXISTS student;",
		"DROP TABLE IF EXISTS instructor;",

		// Create the instructor table
		"CREATE TABLE instructor ("
				+ "id SERIAL PRIMARY KEY, "
				+ "name VARCHAR(255) NOT NULL, "
				+ "email VARCHAR(255) NOT NULL);",

		// Create the course table with FK to instructor
		"CREATE TABLE course ("
				+ "id SERIAL PRIMARY KEY, "
				+ "name VARCHAR(255) NOT NULL, "
				+ "code VARCHAR(255), "
				+ "credit INT, "
				+ "instructor_id INT, "
				+ "CONSTRAINT fk_course_instructor FOREIGN KEY (instructor_id) REFERENCES instructor(id));",

		// Create the student table
		"CREATE TABLE student ("
				+ "id SERIAL PRIMARY KEY, "
				+ "name VARCHAR(255) NOT NULL, "
				+ "email VARCHAR(255) NOT NULL);",

		// Create the join table for many-to-many relationship
		"CREATE TABLE student_course ("
				+ "student_id INT NOT NULL, "
				+ "course_id INT NOT NULL, "
				+ "PRIMARY KEY (student_id, course_id), "
				+ "CONSTRAINT fk_student_course_student FOREIGN KEY (student_id) REFERENCES student(id), "
				+ "CONSTRAINT fk_student_course_course FOREIGN KEY (course_id) REFERENCES course(id));",

		// Seed data:
		// Insert an instructor (assumed to get id=1)
		"INSERT INTO instructor (name, email) VALUES ('Alice Instructor', 'alice.instructor@example.com');",
		// Insert a course assigned to the instructor (assumed to get id=1)
		"INSERT INTO course (name, code, credit, instructor_id) VALUES ('Mathematics', 'MATH101', 3, 1);",
		// Insert three students (so that ids 1, 2, and 3 exist)
		"INSERT INTO student (name, email) VALUES ('John Doe', 'john@example.com');",
		"INSERT INTO student (name, email) VALUES ('Jane Doe', 'jane@example.com');",
		"INSERT INTO student (name, email) VALUES ('Extra Student', 'extra@student.com');",
		// Optionally, enroll one student in the course
		"INSERT INTO student_course (student_id, course_id) VALUES (1, 1);"
}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)

class Task2ApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * GET /students - Retrieve all students
	 */
	@Test
	public void testGetAllStudents() throws Exception {
		mockMvc.perform(get("/students"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}

	/**
	 * GET /students/{id} - Retrieve a student by ID
	 * Assumes that the seeded database contains a student with id=1.
	 */
	@Test
	public void testGetStudentById() throws Exception {
		mockMvc.perform(get("/students/1" +
						""))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1));
	}

	/**
	 * POST /students - Create a new student
	 */
	@Test
	public void testCreateStudent() throws Exception {
		String newStudentJson = "{\"name\": \"Test Student\", \"email\": \"test@student.com\"}";
		mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newStudentJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Test Student"))
				.andExpect(jsonPath("$.email").value("test@student.com"));
	}

	/**
	 * PUT /students/{id} - Update an existing student
	 * First, create a student then update it.
	 */
	@Test
	public void testUpdateStudent() throws Exception {
		// Create a new student
		String studentJson = "{\"name\": \"Update Student\", \"email\": \"update@student.com\"}";
		MvcResult result = mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(studentJson))
				.andExpect(status().isOk())
				.andReturn();

		// Extract the created student's id from the JSON response
		String responseBody = result.getResponse().getContentAsString();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		int createdStudentId = jsonNode.get("id").asInt();

		// Prepare updated JSON payload
		String updatedStudentJson = "{\"name\": \"Updated Name\", \"email\": \"updated@student.com\"}";

		// Update the student
		mockMvc.perform(put("/students/" + createdStudentId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatedStudentJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Updated Name"))
				.andExpect(jsonPath("$.email").value("updated@student.com"));
	}

	/**
	 * DELETE /students/{id} - Delete a student
	 * First, create a student then delete it.
	 */
	@Test
	public void testDeleteStudent() throws Exception {
		// Create a new student
		String studentJson = "{\"name\": \"Delete Student\", \"email\": \"delete@student.com\"}";
		MvcResult result = mockMvc.perform(post("/students")
						.contentType(MediaType.APPLICATION_JSON)
						.content(studentJson))
				.andExpect(status().isOk())
				.andReturn();

		// Extract the created student's id
		String responseBody = result.getResponse().getContentAsString();
		JsonNode jsonNode = objectMapper.readTree(responseBody);
		int createdStudentId = jsonNode.get("id").asInt();

		// Delete the student
		mockMvc.perform(delete("/students/" + createdStudentId))
				.andExpect(status().isOk());

		// Optionally, verify deletion by attempting to get the deleted student (should return 404)
		mockMvc.perform(get("/students" + createdStudentId))
				.andExpect(status().isNotFound());
	}

	/**
	 * GET /students/course/{courseID} - Retrieve students enrolled in a specific course
	 * Note: The original code had ambiguous mapping for course-based retrieval.
	 * For clarity, we assume the endpoint has been modified to /students/course/{courseID}.
	 * Assumes that the seeded database enrolled some students in the course with id=1.
	 */
	@Test
	public void testGetStudentsByCourseId() throws Exception {
		mockMvc.perform(get("/students/course/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}


	/**
	 * GET /instructors
	 * Expects to retrieve all instructors as a JSON array.
	 */
	@Test
	public void testGetAllInstructors() throws Exception {
		mockMvc.perform(get("/instructors"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}

	/**
	 * GET /instructors/{email}
	 * Expects to retrieve a specific instructor by email.
	 * Assumes that the seeded database contains an instructor with email "alice.instructor@example.com"
	 * and name "Alice Instructor".
	 */
	@Test
	public void testGetInstructorByEmail() throws Exception {
		mockMvc.perform(get("/instructors/alice.instructor@example.com"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.email").value("alice.instructor@example.com"))
				.andExpect(jsonPath("$.name").value("Alice Instructor"));
	}
	/**
	 * GET /courses
	 * Retrieves all courses.
	 * Expects a JSON array.
	 */
	@Test
	public void testGetAllCourses() throws Exception {
		mockMvc.perform(get("/courses"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}

	/**
	 * GET /courses/{instructorId}
	 * Retrieves courses taught by a given instructor.
	 * Assumes that the seeded database has at least one course taught by instructor with id=1.
	 */
	@Test
	public void testGetCoursesByInstructor() throws Exception {
		mockMvc.perform(get("/courses/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray());
	}

	/**
	 * POST /courses/{courseId}/students/{studentId}
	 * Enroll a student in a course.
	 * Assumes that course with id=1 and student with id=2 exist.
	 */
	@Test
	public void testEnrollStudentInCourse() throws Exception {
		mockMvc.perform(post("/courses/1/students/2"))
				.andExpect(status().isOk())
				.andExpect(content().string("Student enrolled in course successfully."));
	}

	/**
	 * PUT /courses/{courseId}/students/{studentId}
	 * Unenroll a student from a course.
	 * For testing, first enroll a student (e.g. student with id=3 into course with id=1) and then unenroll them.
	 */
	@Test
	public void testUnenrollStudentFromCourse() throws Exception {
		// Enroll the student first (if not already enrolled)
		mockMvc.perform(post("/courses/1/students/3"))
				.andExpect(status().isOk())
				.andExpect(content().string("Student enrolled in course successfully."));

		// Now, unenroll the same student
		mockMvc.perform(put("/courses/1/students/3"))
				.andExpect(status().isOk())
				.andExpect(content().string("Student unenrolled from course successfully."));
	}
}
