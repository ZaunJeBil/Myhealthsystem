package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health-logs")
public class HealthLogController {

    @Autowired
    private HealthLogRepository repository;

    // 讓前端可以撈取 MySQL 的所有歷史資料
    @GetMapping
    public List<HealthLog> getAllLogs() {
        return repository.findAll();
    }

    // 接收前端傳來的資料並存進 MySQL
    @PostMapping
    public HealthLog createLog(@RequestBody HealthLog log) {
        return repository.save(log);
    }
}
