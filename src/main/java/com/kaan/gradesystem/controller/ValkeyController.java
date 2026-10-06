package com.kaan.gradesystem.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/valkey")
public class ValkeyController {

    private final StringRedisTemplate redisTemplate;

    public ValkeyController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Valkey'e key-value kaydeder.
    @PostMapping
    public String setValue(@RequestParam String key,
                           @RequestParam String value) {

        redisTemplate.opsForValue().set(key, value);

        return "Kaydedildi";
    }

    // Verilen key'in değerini Valkey'den getirir.
    @GetMapping("/{key}")
    public String getValue(@PathVariable String key) {

        return redisTemplate.opsForValue().get(key);
    }

    // Verilen key'i Valkey'den siler.
    @DeleteMapping("/{key}")
    public String deleteValue(@PathVariable String key) {

        redisTemplate.delete(key);

        return "Silindi";
    }
}