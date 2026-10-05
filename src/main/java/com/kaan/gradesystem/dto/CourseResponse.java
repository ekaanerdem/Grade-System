package com.kaan.gradesystem.dto;

public record CourseResponse(
        Long id,
        String name,
        String code,
        String teacherName
) {
}