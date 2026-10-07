package com.kaan.gradesystem.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentKafkaProducer {

    private final KafkaTemplate<String, StudentKafkaEvent> kafkaTemplate;

    public StudentKafkaProducer(
            KafkaTemplate<String, StudentKafkaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(StudentKafkaEvent event) {

        // Mesajı student-events topic'ine gönderir.
        // studentId key olarak kullanıldığı için aynı öğrenciye ait
        // mesajların aynı partition'a gitmesi sağlanır.
        kafkaTemplate.send(
                "student-events",
                event.studentId().toString(),
                event
        );
    }
}