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

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository){
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Grade> getAllGrades(){
        return gradeRepository.findAll();
    }

    public Grade saveGrade(Grade grade){

        Student student = studentRepository.findById(grade.getStudent().getId()).orElse(null);
        Course course = courseRepository.findById(grade.getCourse().getId()).orElse(null);

        grade.setStudent(student);
        grade.setCourse(course);

        return gradeRepository.save(grade);
    }

    public Grade getGradeById(Long id){
        return gradeRepository.findById(id).orElse(null);
    }

    public Grade updateGrade(Long id, Grade grade){
        Grade existingGrade = gradeRepository.findById(id).orElse(null);

        if(existingGrade == null){
            return null;
        }

        Student student = studentRepository
                .findById(grade.getStudent().getId())
                .orElse(null);

        Course course = courseRepository
                .findById(grade.getCourse().getId())
                .orElse(null);

        existingGrade.setScore(grade.getScore());
        existingGrade.setStudent(student);
        existingGrade.setCourse(course);

        return gradeRepository.save(existingGrade);

    }

    public void deleteGrade(Long id){
        gradeRepository.deleteById(id);
    }

}