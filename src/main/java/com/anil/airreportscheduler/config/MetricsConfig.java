package com.anil.airreportscheduler.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter pirepIngestedCounter(MeterRegistry registry) {
        return Counter.builder("aviation.pirep.ingested")
            .description("Number of PIREP reports ingested")
            .register(registry);
    }

    @Bean
    public Counter pirepDuplicateCounter(MeterRegistry registry) {
        return Counter.builder("aviation.pirep.duplicates")
            .description("Number of duplicate PIREP reports skipped")
            .register(registry);
    }

    @Bean
    public Counter metarIngestedCounter(MeterRegistry registry) {
        return Counter.builder("aviation.metar.ingested")
            .description("Number of METAR reports ingested")
            .register(registry);
    }

    @Bean
    public Counter metarDuplicateCounter(MeterRegistry registry) {
        return Counter.builder("aviation.metar.duplicates")
            .description("Number of duplicate METAR reports skipped")
            .register(registry);
    }

    @Bean
    public Counter apiErrorCounter(MeterRegistry registry) {
        return Counter.builder("aviation.api.errors")
            .description("Number of API errors encountered")
            .register(registry);
    }
}
