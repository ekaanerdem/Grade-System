package com.kaan.gradesystem.repository;

import com.kaan.gradesystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}