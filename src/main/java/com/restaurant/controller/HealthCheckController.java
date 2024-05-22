package com.restaurant.controller;

import com.micro.healthcheck.model.HealthCheckResponse;
import com.micro.healthcheck.service.HealthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ComponentScan("com.micro.healthcheck")
@RequestMapping("/api/v1/health-check")
public class HealthCheckController {
    private final HealthCheckService healthCheckService;

    public HealthCheckController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @RequestMapping
    public HealthCheckResponse healthCheck() {
        return healthCheckService.doHealthCheck();
    }
}
