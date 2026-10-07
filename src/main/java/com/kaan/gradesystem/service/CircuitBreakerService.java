package com.kaan.gradesystem.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CircuitBreakerService {

    @CircuitBreaker(
        name = "exampleService",
        fallbackMethod = "fallback"
    )
    public String callService() {

        // Bu yazı görünüyorsa gerçek servis çağrısı yapılmıştır.
        System.out.println("Gerçek servis çağrıldı.");

        // Başka bir servis çalışmıyormuş gibi hata oluşturuyoruz.
        throw new RuntimeException("Servise ulaşılamadı");
    }

    // Ana metot hata verdiğinde bu metot çalışır.
    public String fallback(Throwable throwable) {
        return "Fallback çalıştı. Hata: " + throwable.getClass().getSimpleName();
    }
}