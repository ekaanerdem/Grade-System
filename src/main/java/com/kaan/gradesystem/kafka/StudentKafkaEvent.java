package com.kaan.gradesystem.kafka;

// Kafka'ya gönderilecek öğrenci mesajının yapısı.
public record StudentKafkaEvent(
        String operation,
        Long studentId,
        String name
) {
}