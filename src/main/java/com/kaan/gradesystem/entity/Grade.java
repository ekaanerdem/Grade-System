package com.kaan.gradesystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Grade{

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long id;
private Double score;

@ManyToOne
@JoinColumn(name = "student_id")
private Student student;

@ManyToOne
@JoinColumn(name = "course_id")
private Course course;

//Getter & Setter

public Long getId(){
    return id;
}

public void setId(Long id){
    this.id = id;
}

public Double getScore(){
    return score;
}

public void setScore(Double score){
    this.score = score;
}

public Student getStudent(){
    return student;
}

public void setStudent(Student student){
    this.student = student;
}

public Course getCourse(){
    return course;
}

public void setCourse(Course course){
    this.course = course;
}



}