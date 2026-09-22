package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.dto.GradeRequest;
import com.kaan.gradesystem.dto.GradeResponse;
import com.kaan.gradesystem.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.access.prepost.PreAuthorize;

@Tag(name = "Grade", description = "Not işlemleri")
@RestController
public class GradeController{

    private final GradeService gradeService;

    public GradeController(GradeService gradeService){
        this.gradeService = gradeService;
    }

    @Operation(summary = "Tüm notları listeler")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/grades")
    public List<GradeResponse> getAllGrades(){
        return gradeService.getAllGrades();
    }

    @Operation(summary = "Yeni not oluşturur")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/grades")
    public GradeResponse saveGrade(@RequestBody GradeRequest request){
        return gradeService.saveGrade(request);
    }

    @Operation(summary = "ID'ye göre not getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/grades/{id}")
    public GradeResponse getGradeById(@PathVariable Long id){
        return gradeService.getGradeById(id);
    }

    @Operation(summary = "Öğrenci ID'sine göre notları getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/grades/student/{studentId}")
    public List<GradeResponse> getGradesByStudentId(@PathVariable Long studentId){
        return gradeService.getGradesByStudentId(studentId);
    }

    @Operation(summary = "Minimum puana göre notları getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/grades/min-score/{score}")
    public List<GradeResponse> getGradesByMinimumScore(@PathVariable Double score){
        return gradeService.getGradesByMinimumScore(score);
    }

    @Operation(summary = "Not bilgisini günceller")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/grades/{id}")
    public GradeResponse updateGrade(
            @PathVariable Long id,
            @RequestBody GradeRequest request){

        return gradeService.updateGrade(id, request);
    }

    @Operation(summary = "Notu siler")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/grades/{id}")
    public void deleteGrade(@PathVariable Long id){
        gradeService.deleteGrade(id);
    }
}