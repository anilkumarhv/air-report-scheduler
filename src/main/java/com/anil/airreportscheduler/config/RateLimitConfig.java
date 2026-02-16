package com.anil.airreportscheduler.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Value("${rate.limit.requests:20}")
    private int maxRequests;

    @Value("${rate.limit.duration-minutes:1}")
    private int durationMinutes;

    @Bean
    public Bucket rateLimitBucket() {
        Bandwidth limit = Bandwidth.builder()
            .capacity(maxRequests)
            .refillGreedy(maxRequests, Duration.ofMinutes(durationMinutes))
            .build();
        return Bucket.builder().addLimit(limit).build();
    }
}
