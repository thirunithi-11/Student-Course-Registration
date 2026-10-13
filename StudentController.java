
package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Studentservice;
import com.example.demo.entity.Student;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final Studentservice studentService;

    public StudentController(Studentservice studentService) {
        this.studentService = studentService;
    }

    // Register a new student
    @PostMapping
    public Student registerStudent(@Valid @RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        if (student == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student not found");
        }

        return student;
    }

    // Delete student by ID
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        if (student == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student not found");
        }

        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

    // Update student details
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        Student existingStudent = studentService.getStudentById(id);

        if (existingStudent == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student not found");
        }

        existingStudent.setStudentName(student.getStudentName());
        existingStudent.setCourseName(student.getCourseName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setAge(student.getAge());

        return studentService.registerStudent(existingStudent);
    }
}


