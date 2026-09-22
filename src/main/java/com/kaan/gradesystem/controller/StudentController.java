package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.dto.StudentRequest;
import com.kaan.gradesystem.dto.StudentResponse;
import com.kaan.gradesystem.service.StudentService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Student", description = "Öğrenci işlemleri")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @Operation(summary = "Tüm öğrencileri listeler")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/students")
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }


    @Operation(summary = "Yeni öğrenci oluşturur")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/students")
    public StudentResponse saveStudent(@RequestBody StudentRequest request) {
        return studentService.saveStudent(request);
    }


    @Operation(summary = "ID'ye göre öğrenci getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/students/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }


    @Operation(summary = "Öğrenci bilgilerini günceller")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/students/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @RequestBody StudentRequest request) {

        return studentService.updateStudent(id, request);
    }


    @Operation(summary = "Öğrenciyi siler")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}