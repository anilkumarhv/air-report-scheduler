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
//    @Scheduled(fixedRate=60*60*1000)
    @Async
    public void pirepSchedulerTask() {
        log.info("pirep scheduler started. Execution Start Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            pirepService.getAircraftReportFromAddsServer();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("pirep scheduler completed successfully. Execution end Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

    @Scheduled(cron = "${metar-report-cron:0 0/5 * * * ?}")
    @Async
    public void metarSchedulerTask() {
        log.info("Metar Report scheduler started. Execution Start Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            metarService.getMetarFromAddsServer();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("Metar Report scheduler completed successfully. Execution end Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

}
