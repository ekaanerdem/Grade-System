package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Grade", description = "Not işlemleri")
@RestController
public class GradeController{

    private final GradeService gradeService;

    public GradeController(GradeService gradeService){
        this.gradeService = gradeService;
    }
    @Operation(summary = "Tüm notları listeler")
    @GetMapping("/grades")
    public List<Grade> getAllGrades(){
        return gradeService.getAllGrades();
     }

    @Operation(summary = "Yeni not oluşturur")
    @PostMapping("/grades")
    public Grade saveGrade(@RequestBody Grade grade){
        return gradeService.saveGrade(grade);
     }

    @Operation(summary = "ID'ye göre not getirir")
    @GetMapping("/grades/{id}")
    public Grade getGradeById(@PathVariable Long id){
        return gradeService.getGradeById(id);
    }

    @Operation(summary = "Not bilgisini günceller")
    @PutMapping("/grades/{id}")
    public Grade updateGrade(@PathVariable Long id, @RequestBody Grade grade){
        return gradeService.updateGrade(id, grade);
    }

    @Operation(summary = "Notu siler")
    @DeleteMapping("/grades/{id}")
    public void deleteGrade(@PathVariable Long id){
        gradeService.deleteGrade(id);
    }


}