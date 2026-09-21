package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.repository.CourseRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest{

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getAllCourses_ShouldReturnCourses() {

        Course course1 = new Course();
        course1.setName("Math");

        Course course2 = new Course();
        course2.setName("Biology");

        List<Course> courses = List.of(course1, course2);

        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).getName());
        assertEquals("Biology", result.get(1).getName());

        // ya da direkt assertEquals(courses, result);

        verify(courseRepository).findAll();
    }

    @Test
    void saveCourse_ShouldReturnSavedCourse() {

        Course course = new Course();
        course.setName("Java");

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.saveCourse(course);

        assertEquals("Java", result.getName());

        verify(courseRepository).save(course);
    }

    @Test
    void getCourseById_ShouldReturnCourse() {

        Long id = 1L;

        Course course = new Course();
        course.setName("Java");

        when(courseRepository.findById(id))
                             .thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(id);

        assertEquals("Java", result.getName());

        verify(courseRepository).findById(id);
    }

    @Test
    void getCourseById_ShouldReturnNull_WhenCourseNotFound() {

        Long id = 50L;

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        Course result = courseService.getCourseById(id);

        assertNull(result);

        verify(courseRepository).findById(id);
    }

    @Test
    void updateCourse_ShouldUpdateCourse() {

        Long id = 1L;

        Course existingCourse = new Course();
        existingCourse.setName("Old Java");
        existingCourse.setCode("OLD101");
        existingCourse.setTeacherName("Old Teacher");

        Course newCourse = new Course();
        newCourse.setName("Java");
        newCourse.setCode("JAVA101");
        newCourse.setTeacherName("New Teacher");

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(existingCourse));

        when(courseRepository.save(existingCourse))
                .thenReturn(existingCourse);

        Course result = courseService.updateCourse(id, newCourse);

        assertEquals("Java", result.getName());
        assertEquals("JAVA101", result.getCode());
        assertEquals("New Teacher", result.getTeacherName());

        verify(courseRepository).findById(id);
        verify(courseRepository).save(existingCourse);
    }

    @Test
    void deleteCourse_ShouldDeleteCourse() {

        Long id = 1L;

        courseService.deleteCourse(id);

        verify(courseRepository).deleteById(id);
    }

}