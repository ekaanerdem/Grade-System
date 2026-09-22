package com.kaan.gradesystem.service;

import com.kaan.gradesystem.entity.Grade;
import com.kaan.gradesystem.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticLockDebugService {

    private final GradeRepository gradeRepository;

    public OptimisticLockDebugService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Transactional
    public void updateGradeForDebug(Long id, Double newScore) {

        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade bulunamadı"));

        grade.setScore(newScore);

        gradeRepository.saveAndFlush(grade);
        //flush : "Hibernate, bekleyen değişikliği şimdi veritabanına gönder”
    }
}