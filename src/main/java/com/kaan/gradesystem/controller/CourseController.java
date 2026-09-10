package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Course", description = "Ders işlemleri")
@RestController
public class CourseController{

    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Operation(summary = "Tüm dersleri listeler")
    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @Operation(summary = "Yeni ders oluşturur")
    @PostMapping("/courses")
    public Course saveCourse(@RequestBody Course course){
        return courseService.saveCourse(course);
    }

    @Operation(summary = "ID'ye göre ders getirir")
    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
        }

    @Operation(summary = "Ders bilgilerini günceller")
    @PutMapping("/courses/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course){
        return courseService.updateCourse(id, course);
        }

    @Operation(summary = "Dersi siler")
    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}