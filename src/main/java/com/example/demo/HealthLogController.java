package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health-logs")
public class HealthLogController {

    @Autowired
    private HealthLogRepository repository;

    // 1. 取得所有歷史日誌 (日期最新在最前)
    @GetMapping
    public List<HealthLog> getAllLogs() {
        return repository.findAll().stream()
                .sorted((b, a) -> a.getLogDate().compareTo(b.getLogDate()))
                .toList();
    }

    // 2. 新增今日健康日誌
    @PostMapping
    public HealthLog createLog(@RequestBody HealthLog log) {
        calculateRisk(log);
        return repository.save(log);
    }

    // 3. 【新功能】修改/更新特定日誌 (根據 ID)
    @PutMapping("/{id}")
    public HealthLog updateLog(@PathVariable Long id, @RequestBody HealthLog newLog) {
        return repository.findById(id)
                .map(log -> {
                    log.setLogDate(newLog.getLogDate());
                    log.setSleepHours(newLog.getSleepHours());
                    log.setSteps(newLog.getSteps());
                    log.setMoodScore(newLog.getMoodScore());
                    calculateRisk(log); // 重新以代數決策樹計算新分數的風險
                    return repository.save(log);
                })
                .orElseGet(() -> {
                    newLog.setId(id);
                    calculateRisk(newLog);
                    return repository.save(newLog);
                });
    }

    // 4. 【新功能】刪除特定日誌 (根據 ID)
    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // 將代數決策樹獨立成一個通用方法，確保新增、修改時的邏輯完全一致
    private void calculateRisk(HealthLog log) {
        double sleep = log.getSleepHours() != null ? log.getSleepHours() : 0.0;
        int steps = log.getSteps() != null ? log.getSteps() : 0;
        int mood = log.getMoodScore() != null ? log.getMoodScore() : 5;

        String calculatedRisk = "LOW";

        if (sleep < 6.0) {
            if (steps < 4000) { calculatedRisk = "HIGH"; }
            else { if (mood < 4) { calculatedRisk = "HIGH"; } 
                   else { calculatedRisk = "MEDIUM"; } }
        } else {
            if (steps < 3000) {
                if (mood < 5) { calculatedRisk = "MEDIUM"; }
                else { calculatedRisk = "LOW"; }
            }
        }
        log.setRiskLevel(calculatedRisk);
    }
}
