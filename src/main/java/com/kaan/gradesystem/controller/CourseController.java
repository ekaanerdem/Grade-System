package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.entity.Course;
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
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @Operation(summary = "Yeni ders oluşturur")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/courses")
    public Course saveCourse(@RequestBody Course course){
        return courseService.saveCourse(course);
    }

    @Operation(summary = "ID'ye göre ders getirir")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
        }

    @Operation(summary = "Ders bilgilerini günceller")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course){
        return courseService.updateCourse(id, course);
        }

    @Operation(summary = "Dersi siler")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}