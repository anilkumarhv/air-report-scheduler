package com.anil.airreportscheduler.scheduler;

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

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public PirepScheduler(PirepService pirepService) {
        this.pirepService = pirepService;
    }

    //    @Scheduled(fixedDelay = 60000)
//    @Scheduled(cron = "${name-of-the-cron:0 0 0/1 * * ?}")
    @Scheduled(fixedRate=60*60*1000)
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

}
