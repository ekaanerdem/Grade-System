package com.kaan.gradesystem.repository;

import com.kaan.gradesystem.entity.Grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query(
        value = "SELECT * FROM grade WHERE student_id = :studentId", //:studentId = sonradan verilecek değer için yer tutucu
        nativeQuery = true
    )
    // Bir öğrencinin birden fazla notu olabilir bu nedenle List
    List<Grade> findGradesByStudentIdNative(
        @Param("studentId") Long studentId //Java metoduna gelen studentId değerini sorgudaki :studentId yerine koy
    );

    @Query("SELECT g FROM Grade g WHERE g.score >= :score")
    List<Grade> findGradesByMinimumScore(
        @Param("score") Double score
    );

}
