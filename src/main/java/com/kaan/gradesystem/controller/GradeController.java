package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GradeController{

    private final GradeService gradeService;

    public GradeController(GradeService gradeService){
        this.gradeService = gradeService;
    }

    @GetMapping("/grades")
    public List<Grade> getAllGrades(){
        return gradeService.getAllGrades();
     }

    @PostMapping("/grades")
    public Grade saveGrade(@RequestBody Grade grade){
        return gradeService.saveGrade(grade);
     }

    @GetMapping("/grades/{id}")
    public Grade getGradeById(@PathVariable Long id){
        return gradeService.getGradeById(id);
    }

    @PutMapping("/grades/{id}")
    public Grade updateGrade(@PathVariable Long id, @RequestBody Grade grade){
        return gradeService.updateGrade(id, grade);
    }

    @DeleteMapping("/grades/{id}")
    public void deleteGrade(@PathVariable Long id){
        gradeService.deleteGrade(id);
    }


}