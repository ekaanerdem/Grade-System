package com.kaan.gradesystem.controller;

import com.kaan.gradesystem.service.OptimisticLockDebugService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
public class OptimisticLockDebugController {

    private final OptimisticLockDebugService optimisticLockDebugService;

    public OptimisticLockDebugController(
            OptimisticLockDebugService optimisticLockDebugService) {
        this.optimisticLockDebugService = optimisticLockDebugService;
    }

    @PutMapping("/grades/{id}")
    public String updateGradeForDebug(
            @PathVariable Long id,
            @RequestParam Double score) {

        optimisticLockDebugService.updateGradeForDebug(id, score);

        return "Grade güncellendi";
    }
}