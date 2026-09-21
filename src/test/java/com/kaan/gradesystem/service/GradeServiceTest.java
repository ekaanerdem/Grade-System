package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.CourseRepository;
import com.kaan.gradesystem.repository.GradeRepository;
import com.kaan.gradesystem.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest{

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private GradeService gradeService;


    @Test
    void getAllGrades_ShouldReturnGrades() {

        Grade grade1 = new Grade();
        grade1.setScore(80.0);

        Grade grade2 = new Grade();
        grade2.setScore(95.0);

        List<Grade> grades = List.of(grade1, grade2);

        when(gradeRepository.findAll()).thenReturn(grades);

        List<Grade> result = gradeService.getAllGrades();

        assertEquals(grades, result);

        /* ya da :
        assertEquals(2, result.size());
        assertEquals(80.0, result.get(0).getScore());
        assertEquals(95.0, result.get(1).getScore()); */

        verify(gradeRepository).findAll(); 
    }

    @Test
    void saveGrade_ShouldReturnSavedGrade() {

        Long studentId = 1L;
        Long courseId = 2L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade grade = new Grade();
        grade.setScore(90.0);
        grade.setStudent(student);
        grade.setCourse(course);

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        when(gradeRepository.save(grade))
                .thenReturn(grade);

        Grade result = gradeService.saveGrade(grade);

        assertEquals(grade, result);
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());
        assertEquals(90.0, result.getScore());

        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(gradeRepository).save(grade);
    }

    @Test 
    void saveGrade_ShouldThrowException_WhenStudentNotFound() {

        Long studentId = 99L;
        Long courseId = 2L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade grade = new Grade();
        grade.setScore(90.0);
        grade.setStudent(student);
        grade.setCourse(course);

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.saveGrade(grade)
        );

        assertEquals("Student bulunamadı", exception.getMessage());

        verify(studentRepository).findById(studentId);
    }

    @Test
    void saveGrade_ShouldThrowException_WhenCourseNotFound() {

        Long studentId = 1L;
        Long courseId = 99L;

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade grade = new Grade();
        grade.setScore(90.0);
        grade.setStudent(student);
        grade.setCourse(course);

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.saveGrade(grade)
        );

        assertEquals("Course bulunamadı", exception.getMessage());

        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
    }

    @Test
    void getGradeById_ShouldReturnGrade() {

        Long id = 1L;

        Grade grade = new Grade();
        grade.setScore(85.0);

        when(gradeRepository.findById(id))
                .thenReturn(Optional.of(grade));

        Grade result = gradeService.getGradeById(id);

        assertEquals(grade, result);

        verify(gradeRepository).findById(id);
    }

    @Test
    void getGradeById_ShouldThrowException_WhenGradeNotFound() {

        Long id = 99L;

        when(gradeRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.getGradeById(id)
        );

        assertEquals("Grade bulunamadı", exception.getMessage());

        verify(gradeRepository).findById(id);
    }   

    @Test
    void updateGrade_ShouldUpdateGrade() {

        Long gradeId = 1L;
        Long studentId = 2L;
        Long courseId = 3L;

        Grade existingGrade = new Grade();
        existingGrade.setScore(50.0);

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade newGrade = new Grade();
        newGrade.setScore(95.0);
        newGrade.setStudent(student);
        newGrade.setCourse(course);

        when(gradeRepository.findById(gradeId))
                .thenReturn(Optional.of(existingGrade));

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        when(gradeRepository.save(existingGrade))
                .thenReturn(existingGrade);

        Grade result = gradeService.updateGrade(gradeId, newGrade);

        assertEquals(95.0, result.getScore());
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());

        verify(gradeRepository).findById(gradeId);
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);
        verify(gradeRepository).save(existingGrade);
    }

    @Test
    void deleteGrade_ShouldDeleteGrade() {

        Long id = 1L;

        gradeService.deleteGrade(id);

        verify(gradeRepository).deleteById(id);
    }

    /*@Test
    void updateGrade_ShouldThrowException_WhenGradeNotFound() {

        Long gradeId = 99L;

        Grade newGrade = new Grade();

        when(gradeRepository.findById(gradeId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.updateGrade(gradeId, newGrade)
        );

        assertEquals("Grade bulunamadı", exception.getMessage());

        verify(gradeRepository).findById(gradeId);
    }

    @Test
    void updateGrade_ShouldThrowException_WhenStudentNotFound() {

        Long gradeId = 1L;
        Long studentId = 99L;
        Long courseId = 2L;

        Grade existingGrade = new Grade();

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade newGrade = new Grade();
        newGrade.setScore(90.0);
        newGrade.setStudent(student);
        newGrade.setCourse(course);

        when(gradeRepository.findById(gradeId))
                .thenReturn(Optional.of(existingGrade));

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.updateGrade(gradeId, newGrade)
        );

        assertEquals("Student bulunamadı", exception.getMessage());

        verify(gradeRepository).findById(gradeId);
        verify(studentRepository).findById(studentId);

        verify(courseRepository, never()).findById(courseId);
        verify(gradeRepository, never()).save(existingGrade);
    }

    @Test
    void updateGrade_ShouldThrowException_WhenCourseNotFound() {

        Long gradeId = 1L;
        Long studentId = 2L;
        Long courseId = 99L;

        Grade existingGrade = new Grade();

        Student student = new Student();
        student.setId(studentId);

        Course course = new Course();
        course.setId(courseId);

        Grade newGrade = new Grade();
        newGrade.setScore(90.0);
        newGrade.setStudent(student);
        newGrade.setCourse(course);

        when(gradeRepository.findById(gradeId))
                .thenReturn(Optional.of(existingGrade));

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> gradeService.updateGrade(gradeId, newGrade)
        );

        assertEquals("Course bulunamadı", exception.getMessage());

        verify(gradeRepository).findById(gradeId);
        verify(studentRepository).findById(studentId);
        verify(courseRepository).findById(courseId);

        verify(gradeRepository, never()).save(existingGrade);
    }*/









}