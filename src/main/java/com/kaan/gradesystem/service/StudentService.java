package com.kaan.gradesystem.service;

import com.kaan.gradesystem.dto.StudentRequest;
import com.kaan.gradesystem.dto.StudentResponse;
import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<StudentResponse> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responses = new ArrayList<>();

        for (Student student : students) {

            StudentResponse response = new StudentResponse();

            response.setId(student.getId());
            response.setName(student.getName());
            response.setSurname(student.getSurname());
            response.setEmail(student.getEmail());

            responses.add(response);
        }

        return responses;
    }


    public StudentResponse saveStudent(StudentRequest request) {

        Student student = new Student();

        student.setName(request.getName());
        student.setSurname(request.getSurname());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());

        Student savedStudent = studentRepository.save(student);

        StudentResponse response = new StudentResponse();

        response.setId(savedStudent.getId());
        response.setName(savedStudent.getName());
        response.setSurname(savedStudent.getSurname());
        response.setEmail(savedStudent.getEmail());

        return response;
    }


    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setName(student.getName());
        response.setSurname(student.getSurname());
        response.setEmail(student.getEmail());

        return response;
    }


    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return null;
        }

        existingStudent.setName(request.getName());
        existingStudent.setSurname(request.getSurname());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setPassword(request.getPassword());

        Student updatedStudent = studentRepository.save(existingStudent);

        StudentResponse response = new StudentResponse();

        response.setId(updatedStudent.getId());
        response.setName(updatedStudent.getName());
        response.setSurname(updatedStudent.getSurname());
        response.setEmail(updatedStudent.getEmail());

        return response;
    }


    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}