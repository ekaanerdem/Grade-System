package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Student;
import com.kaan.gradesystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentService{

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student){

        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent == null){
            return null;
        }

        existingStudent.setName(student.getName());
        existingStudent.setSurname(student.getSurname());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPassword(student.getPassword());

        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }


}