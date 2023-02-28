package com.anil.airreportscheduler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String health() {
        return "Air Report scheduler is up and running!!!";
    }
}
