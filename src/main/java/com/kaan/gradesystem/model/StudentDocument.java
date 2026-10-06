package com.kaan.gradesystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "students") 
//JPA'daki @Entity mantığı ama burada PostgreSQL tablosunu değil, Elasticsearch'teki students index'ini temsil ediyor.
public class StudentDocument {

    @Id
    private Long id;

    private String name;
    private String department;

    public StudentDocument() {
    }

    public StudentDocument(Long id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}