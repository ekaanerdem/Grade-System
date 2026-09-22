package com.kaan.gradesystem.service;

import com.kaan.gradesystem.dto.GradeRequest;
import com.kaan.gradesystem.dto.GradeResponse;
import com.kaan.gradesystem.entity.Course;
import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.CourseRepository;
import com.kaan.gradesystem.repository.GradeRepository;
import com.kaan.gradesystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<GradeResponse> getAllGrades(){

        List<Grade> grades = gradeRepository.findAll();
        List<GradeResponse> responses = new ArrayList<>();

        for (Grade grade : grades){

            GradeResponse response = new GradeResponse();

            response.setId(grade.getId());
            response.setScore(grade.getScore());
            response.setVersion(grade.getVersion());
            response.setStudentId(grade.getStudent().getId());
            response.setCourseId(grade.getCourse().getId());

            responses.add(response);
        }

        return responses;
    }

    public GradeResponse saveGrade(GradeRequest request) {

        Student student = studentRepository
                .findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student bulunamadı"));

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course bulunamadı"));

        Grade grade = new Grade();

        grade.setScore(request.getScore());
        grade.setStudent(student);
        grade.setCourse(course);

        Grade savedGrade = gradeRepository.save(grade);

        GradeResponse response = new GradeResponse();

        response.setId(savedGrade.getId());
        response.setScore(savedGrade.getScore());
        response.setVersion(savedGrade.getVersion());
        response.setStudentId(savedGrade.getStudent().getId());
        response.setCourseId(savedGrade.getCourse().getId());

        return response;
    }

    public GradeResponse getGradeById(Long id){

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade bulunamadı"));

        GradeResponse response = new GradeResponse();

        response.setId(grade.getId());
        response.setScore(grade.getScore());
        response.setVersion(grade.getVersion());
        response.setStudentId(grade.getStudent().getId());
        response.setCourseId(grade.getCourse().getId());

        return response;
    }

    public GradeResponse updateGrade(Long id, GradeRequest request) {

        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade bulunamadı"));

        Student student = studentRepository
                .findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student bulunamadı"));

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course bulunamadı"));

        existingGrade.setScore(request.getScore());
        existingGrade.setStudent(student);
        existingGrade.setCourse(course);

        Grade updatedGrade = gradeRepository.save(existingGrade);

        GradeResponse response = new GradeResponse();

        response.setId(updatedGrade.getId());
        response.setScore(updatedGrade.getScore());
        response.setVersion(updatedGrade.getVersion());
        response.setStudentId(updatedGrade.getStudent().getId());
        response.setCourseId(updatedGrade.getCourse().getId());

        return response;
    }

    public List<GradeResponse> getGradesByStudentId(Long studentId) {

        List<Grade> grades =
                gradeRepository.findGradesByStudentIdNative(studentId);

        List<GradeResponse> responses = new ArrayList<>();

        for (Grade grade : grades) {

            GradeResponse response = new GradeResponse();

            response.setId(grade.getId());
            response.setScore(grade.getScore());
            response.setVersion(grade.getVersion());
            response.setStudentId(grade.getStudent().getId());
            response.setCourseId(grade.getCourse().getId());

            responses.add(response);
        }

        return responses;
    }

    public List<GradeResponse> getGradesByMinimumScore(Double score) {

        List<Grade> grades =
                gradeRepository.findGradesByMinimumScore(score);

        List<GradeResponse> responses = new ArrayList<>();

        for (Grade grade : grades) {

            GradeResponse response = new GradeResponse();

            response.setId(grade.getId());
            response.setScore(grade.getScore());
            response.setVersion(grade.getVersion());
            response.setStudentId(grade.getStudent().getId());
            response.setCourseId(grade.getCourse().getId());

            responses.add(response);
        }

        return responses;
    }   

    public void deleteGrade(Long id){
        gradeRepository.deleteById(id);
    }
}