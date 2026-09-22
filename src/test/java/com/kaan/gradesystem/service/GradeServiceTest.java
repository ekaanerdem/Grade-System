package com.kaan.gradesystem.service;

import com.kaan.gradesystem.dto.GradeRequest;
import com.kaan.gradesystem.dto.GradeResponse;
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
import static org.mockito.ArgumentMatchers.any;

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

        Student student = new Student();
        student.setId(1L);

        Course course = new Course();
        course.setId(2L);

        Grade grade1 = new Grade();
        grade1.setScore(80.0);
        grade1.setStudent(student);
        grade1.setCourse(course);

        Grade grade2 = new Grade();
        grade2.setScore(95.0);
        grade2.setStudent(student);
        grade2.setCourse(course);

        List<Grade> grades = List.of(grade1, grade2);

        when(gradeRepository.findAll()).thenReturn(grades);

        List<GradeResponse> result = gradeService.getAllGrades();

        assertEquals(2, result.size());
        assertEquals(80.0, result.get(0).getScore());
        assertEquals(95.0, result.get(1).getScore());

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

    GradeRequest grade = new GradeRequest();
    grade.setScore(90.0);
    grade.setStudentId(studentId);
    grade.setCourseId(courseId);

    Grade savedGrade = new Grade();
    savedGrade.setScore(90.0);
    savedGrade.setStudent(student);
    savedGrade.setCourse(course);

    when(studentRepository.findById(studentId))
            .thenReturn(Optional.of(student));

    when(courseRepository.findById(courseId))
            .thenReturn(Optional.of(course));

    /*
    DTO kullanmadan önce:
    when(gradeRepository.save(grade))
            .thenReturn(grade);

    diyebiliyorduk.

    Ama artık Service, GradeRequest'ten kendi Grade nesnesini oluşturduğu için
    testteki Grade ile Service'in oluşturduğu Grade aynı nesne değil.
    Bu yüzden herhangi bir Grade geldiğinde anlamında any(Grade.class) kullanıyoruz.
    */
    when(gradeRepository.save(any(Grade.class)))
            .thenReturn(savedGrade);

    GradeResponse result = gradeService.saveGrade(grade);

    assertEquals(studentId, result.getStudentId());
    assertEquals(courseId, result.getCourseId());
    assertEquals(90.0, result.getScore());

    verify(studentRepository).findById(studentId);
    verify(courseRepository).findById(courseId);
    verify(gradeRepository).save(any(Grade.class));
}

    @Test 
    void saveGrade_ShouldThrowException_WhenStudentNotFound() {

        Long studentId = 99L;
        Long courseId = 2L;

        GradeRequest grade = new GradeRequest();
        grade.setScore(90.0);
        grade.setStudentId(studentId);
        grade.setCourseId(courseId);

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

        GradeRequest grade = new GradeRequest();
        grade.setScore(90.0);
        grade.setStudentId(studentId);
        grade.setCourseId(courseId);

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

        Student student = new Student();
        student.setId(2L);

        Course course = new Course();
        course.setId(3L);

        Grade grade = new Grade();
        grade.setScore(85.0);
        grade.setStudent(student);
        grade.setCourse(course);

        when(gradeRepository.findById(id))
                .thenReturn(Optional.of(grade));

        GradeResponse result = gradeService.getGradeById(id);

        assertEquals(85.0, result.getScore());
        assertEquals(2L, result.getStudentId());
        assertEquals(3L, result.getCourseId());

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

        GradeRequest newGrade = new GradeRequest();
        newGrade.setScore(95.0);
        newGrade.setStudentId(studentId);
        newGrade.setCourseId(courseId);

        when(gradeRepository.findById(gradeId))
                .thenReturn(Optional.of(existingGrade));

        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));

        when(gradeRepository.save(existingGrade))
                .thenReturn(existingGrade);

        GradeResponse result = gradeService.updateGrade(gradeId, newGrade);

        assertEquals(95.0, result.getScore());
        assertEquals(studentId, result.getStudentId());
        assertEquals(courseId, result.getCourseId());

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

        GradeRequest newGrade = new GradeRequest();

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

        GradeRequest newGrade = new GradeRequest();
        newGrade.setScore(90.0);
        newGrade.setStudentId(studentId);
        newGrade.setCourseId(courseId);

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

        GradeRequest newGrade = new GradeRequest();
        newGrade.setScore(90.0);
        newGrade.setStudentId(studentId);
        newGrade.setCourseId(courseId);

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