package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.dto.CourseRequest;
import com.kaan.gradesystem.dto.CourseResponse;
import com.kaan.gradesystem.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.security.access.prepost.PreAuthorize;

@Tag(name = "Course", description = "Ders işlemleri")
@RestController
public class CourseController{

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Operation(summary = "Tüm dersleri listeler")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/courses")
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourses();
    }

    @Operation(summary = "Yeni ders oluşturur")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/courses")
    public CourseResponse saveCourse(@RequestBody CourseRequest request){
        return courseService.saveCourse(request);
    }

    @Operation(summary = "ID'ye göre ders getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/courses/{id}")
    public CourseResponse getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Ders bilgilerini günceller")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/courses/{id}")
    public CourseResponse updateCourse(
            @PathVariable Long id,
            @RequestBody CourseRequest request){

        return courseService.updateCourse(id, request);
    }

    @Operation(summary = "Dersi siler")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}