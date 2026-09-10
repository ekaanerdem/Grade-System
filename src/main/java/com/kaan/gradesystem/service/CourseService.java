package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService{
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course saveCourse(Course course){
        return courseRepository.save(course);
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id).orElse(null);
    }

    public Course updateCourse(Long id, Course course){

    Course existingCourse = courseRepository.findById(id).orElse(null);

    if(existingCourse == null){
        return null;
    }

    existingCourse.setName(course.getName());
    existingCourse.setCode(course.getCode());
    existingCourse.setTeacherName(course.getTeacherName());

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }
}