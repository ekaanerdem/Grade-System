package com.kaan.gradesystem.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentKafkaConsumer {

    @KafkaListener(
            topics = "student-events",
            groupId = "grade-system-group"
    )
    public void consume(StudentKafkaEvent event) {

        // Kafka'dan gelen öğrenci eventini okur.
        System.out.println(
                "Kafka mesajı alındı: " +
                event.operation() + " - " +
                event.studentId() + " - " +
                event.name()
        );
    }
}

/*KafkaTemplate → Producer'ın Kafka'ya mesaj göndermesini sağlar.

@KafkaListener → Consumer'ın topic'i dinlemesini sağlar.

@EnableKafka → Kafka listener altyapısını etkinleştirmek için kullanılan annotation. 
Spring Boot çoğu gerekli yapılandırmayı otomatik yaptığı için her projede elle eklemek gerekmeyebilir.

ConsumerRecord → Sadece mesajın kendisini değil; key, partition, offset, timestamp gibi 
Kafka bilgilerini de almak istediğinde kullanılır.

@TopicPartition → Listener'ın belirli partition'ları dinlemesini istediğinde kullanılabilir. */