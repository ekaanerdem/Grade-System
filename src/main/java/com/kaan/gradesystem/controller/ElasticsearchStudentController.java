package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.model.StudentDocument;
import com.kaan.gradesystem.repository.StudentElasticsearchRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elasticsearch/students")
public class ElasticsearchStudentController {

    private final StudentElasticsearchRepository repository;

    public ElasticsearchStudentController(StudentElasticsearchRepository repository) {
        this.repository = repository;
    }

    // Elasticsearch'e öğrenci ekler.
    @PostMapping
    public StudentDocument createStudent(@RequestBody StudentDocument student) {
        return repository.save(student);
    }

    // Elasticsearch'teki tüm öğrencileri getirir.
    @GetMapping
    public Iterable<StudentDocument> getAllStudents() {
        return repository.findAll();
    }

    // ID'ye göre öğrenci getirir.
    @GetMapping("/{id}")
    public StudentDocument getStudent(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Elasticsearch'teki öğrenciyi günceller.
    @PutMapping("/{id}")
    public StudentDocument updateStudent(@PathVariable Long id,
                                         @RequestBody StudentDocument student) {

        student.setId(id);
        return repository.save(student);
    }

    // Elasticsearch'teki öğrenciyi siler.
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        repository.deleteById(id);
        return "Öğrenci Elasticsearch'ten silindi";
    }
}