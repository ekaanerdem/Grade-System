package com.kaan.gradesystem.repository;

import com.kaan.gradesystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}