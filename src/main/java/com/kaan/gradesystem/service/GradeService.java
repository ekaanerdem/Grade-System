package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.CourseRepository;
import com.kaan.gradesystem.repository.GradeRepository;
import com.kaan.gradesystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService{

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GradeService(GradeRepository gradeRepository, 
                        StudentRepository studentRepository, 
                        CourseRepository courseRepository){
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Grade> getAllGrades(){
        return gradeRepository.findAll();
    }

    public Grade saveGrade(Grade grade) {

        Student student = studentRepository
                .findById(grade.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student bulunamadı"));

        Course course = courseRepository
                .findById(grade.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course bulunamadı"));

        grade.setStudent(student);
        grade.setCourse(course);

        return gradeRepository.save(grade);
    }

    public Grade getGradeById(Long id){
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade bulunamadı"));
    }

    public Grade updateGrade(Long id, Grade grade) {

        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade bulunamadı"));

        Student student = studentRepository
                .findById(grade.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student bulunamadı"));

        Course course = courseRepository
                .findById(grade.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course bulunamadı"));

        existingGrade.setScore(grade.getScore());
        existingGrade.setStudent(student);
        existingGrade.setCourse(course);

        return gradeRepository.save(existingGrade);
    }

    public void deleteGrade(Long id){
        gradeRepository.deleteById(id);
    }

}