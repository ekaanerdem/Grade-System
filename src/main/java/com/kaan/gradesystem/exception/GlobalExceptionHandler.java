package com.kaan.gradesystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
//“Bu sınıf controller'lardan çıkan hataları merkezi olarak takip edecek ve yakalayacak.”
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class) 
    //“Eğer ObjectOptimisticLockingFailureException oluşursa bu metodu çalıştır.”
    public ResponseEntity<String> handleOptimisticLockException(
            ObjectOptimisticLockingFailureException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Bu kayıt başka bir kullanıcı tarafından güncellendi. Güncel veriyi alıp tekrar deneyin.");
    }
}