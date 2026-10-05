package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.repository.CourseRepository;
import com.kaan.gradesystem.dto.CourseRequest;
import com.kaan.gradesystem.dto.CourseResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<CourseResponse> getAllCourses(){

        List<Course> courses = courseRepository.findAll();

        List<CourseResponse> responses = new ArrayList<>();

        for(Course course : courses){
            responses.add(convertToResponse(course));
        }

        return responses;
    }

    public CourseResponse saveCourse(CourseRequest request){

        Course course = new Course();

        course.setName(request.name());
        course.setCode(request.code());
        course.setTeacherName(request.teacherName());

        Course savedCourse = courseRepository.save(course);

        return convertToResponse(savedCourse);
    }

    public CourseResponse getCourseById(Long id){

        Course course = courseRepository.findById(id).orElse(null);

        if(course == null){
            return null;
        }

        return convertToResponse(course);
    }

    public CourseResponse updateCourse(Long id, CourseRequest request){

        Course existingCourse = courseRepository.findById(id).orElse(null);

        if(existingCourse == null){
            return null;
        }

        existingCourse.setName(request.name());
        existingCourse.setCode(request.code());
        existingCourse.setTeacherName(request.teacherName());

        Course updatedCourse = courseRepository.save(existingCourse);

        return convertToResponse(updatedCourse);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }

    private CourseResponse convertToResponse(Course course){

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getCode(),
                course.getTeacherName()
        );
    }
}