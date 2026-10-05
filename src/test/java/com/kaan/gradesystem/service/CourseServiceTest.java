package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.repository.CourseRepository;
import com.kaan.gradesystem.dto.CourseRequest;
import com.kaan.gradesystem.dto.CourseResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
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

        List<CourseResponse> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        assertEquals("Math", result.get(0).name());
        assertEquals("Biology", result.get(1).name());

        // ya da direkt assertEquals(courses, result);
        // DTO kullandığımız için artık courses ve result farklı türlerde olduğu için
        // bu yöntem kullanılmaz.

        verify(courseRepository).findAll();
    }

    @Test
    void saveCourse_ShouldReturnSavedCourse() {

        CourseRequest request = new CourseRequest(
                "Java",
                null,
                null
        );

        Course savedCourse = new Course();
        savedCourse.setName("Java");

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponse result = courseService.saveCourse(request);

        assertEquals("Java", result.name());

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void getCourseById_ShouldReturnCourse() {

        Long id = 1L;

        Course course = new Course();
        course.setName("Java");

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(course));

        CourseResponse result = courseService.getCourseById(id);

        assertEquals("Java", result.name());

        verify(courseRepository).findById(id);
    }

    @Test
    void getCourseById_ShouldReturnNull_WhenCourseNotFound() {

        Long id = 50L;

        when(courseRepository.findById(id))
                .thenReturn(Optional.empty());

        CourseResponse result = courseService.getCourseById(id);

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

        CourseRequest newCourse = new CourseRequest(
                "Java",
                "JAVA101",
                "New Teacher"
        );

        when(courseRepository.findById(id))
                .thenReturn(Optional.of(existingCourse));

        when(courseRepository.save(existingCourse))
                .thenReturn(existingCourse);

        CourseResponse result = courseService.updateCourse(id, newCourse);

        assertEquals("Java", result.name());
        assertEquals("JAVA101", result.code());
        assertEquals("New Teacher", result.teacherName());

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