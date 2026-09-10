package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.service.StudentService;
import org.springframework.web.bind.annotation.RestController;

//GET
import com.kaan.gradesystem.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

//POST
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//GET BY ID
import org.springframework.web.bind.annotation.PathVariable;

//PUT
import org.springframework.web.bind.annotation.PutMapping;

//DELETE
import org.springframework.web.bind.annotation.DeleteMapping;

// YA DA DİREK TOPLU ŞU ŞEKLDE YAZABİLİRSİN;
//import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student", description = "Öğrenci işlemleri")

@RestController
public class StudentController{

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

@Operation(summary = "Tüm öğrencileri listeler")
@GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
}

@Operation(summary = "Yeni öğrenci oluşturur")
@PostMapping("/students")
    public Student saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
}

@Operation(summary = "ID'ye göre öğrenci getirir")
@GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

@Operation(summary = "Öğrenci bilgilerini günceller")
@PutMapping("/students/{id}")
public Student updateStudent(@PathVariable Long id, @RequestBody Student student){
    return studentService.updateStudent(id, student);
}

@Operation(summary = "Öğrenciyi siler")
@DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

}

