package com.kaan.gradesystem.repository;

import com.kaan.gradesystem.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}