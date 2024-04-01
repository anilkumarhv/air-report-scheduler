package com.anil.airreportscheduler.controller;

import com.anil.airreportscheduler.model.Metar;
import com.anil.airreportscheduler.model.MetarReportResponse;
import com.anil.airreportscheduler.service.MetarService;
import com.anil.airreportscheduler.service.PirepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/dataserver/aviation")
@Slf4j
public class AviationController {

    private final MetarService metarService;
    private final PirepService pirepService;

    public AviationController(MetarService metarService, PirepService pirepService) {
        this.metarService = metarService;
        this.pirepService = pirepService;
    }

    @PostMapping("/metars")
    public List<String> getMetar(@RequestParam(name = "station") String station,
                                 @RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                                 @RequestParam(name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {
        ResponseEntity<MetarReportResponse> response = metarService.updateMetarsFromAddsServer(station, startTime, endTime);
        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData().stream()
                    .map(Metar::getRawText)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping("/metars")
    public List<String> getMetars(@RequestParam(name = "station") String station,
                                 @RequestParam(name = "startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
                                 @RequestParam(name = "endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {
        ResponseEntity<MetarReportResponse> response = metarService.getMetarsFromAddsServer(station, startTime, endTime);
        if (response.getBody() != null && response.getBody().getData() != null) {
            return response.getBody().getData().stream()
                    .map(Metar::getRawText)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
