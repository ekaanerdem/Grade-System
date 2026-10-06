package com.kaan.gradesystem.repository;

import com.kaan.gradesystem.model.StudentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudentElasticsearchRepository
        extends ElasticsearchRepository<StudentDocument, Long> {
}