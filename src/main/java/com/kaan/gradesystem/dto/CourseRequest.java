package com.kaan.gradesystem.dto;

public record CourseRequest(
        String name,
        String code,
        String teacherName
) {
}