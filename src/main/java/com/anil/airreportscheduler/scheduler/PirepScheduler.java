package com.anil.airreportscheduler.scheduler;

import com.anil.airreportscheduler.service.MetarService;
import com.anil.airreportscheduler.service.PirepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class PirepScheduler {

    private final PirepService pirepService;
    private final MetarService metarService;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public PirepScheduler(PirepService pirepService, MetarService metarService) {
        this.pirepService = pirepService;
        this.metarService = metarService;
    }

    @Scheduled(cron = "${pirep-scheduler-cron:0 0/10 * * * ?}")
    @Async
    public void pirepSchedulerTask() {
        log.info("PIREP scheduler started at {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            pirepService.getAircraftReportFromAddsServer();
            log.info("PIREP scheduler completed successfully at {}", dateTimeFormatter.format(LocalDateTime.now()));
        } catch (RuntimeException e) {
            log.error("PIREP scheduler failed: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "${metar-report-cron:0 0/5 * * * ?}")
    @Async
    public void metarSchedulerTask() {
        log.info("METAR scheduler started at {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            metarService.getMetarFromAddsServer();
            log.info("METAR scheduler completed successfully at {}", dateTimeFormatter.format(LocalDateTime.now()));
        } catch (RuntimeException e) {
            log.error("METAR scheduler failed: {}", e.getMessage(), e);
        }
    }

}
