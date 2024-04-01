package com.anil.airreportscheduler.service;

import com.anil.airreportscheduler.model.*;
import com.anil.airreportscheduler.repository.MetarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MetarService {

    private final NOAAADDSService noaaaddsService;

    private final MetarRepository metarRepository;

    public MetarService(NOAAADDSService noaaaddsService, MetarRepository metarRepository) {
        this.noaaaddsService = noaaaddsService;
        this.metarRepository = metarRepository;
    }

    public void getMetarFromAddsServer() {
        try {
            ResponseEntity<MetarReportResponse> metarResponseEntity = noaaaddsService.getAircraftReportFromAddsServer(MetarReportResponse.class, ReportType.METAR.getReportType(), getMetarRequestParam());
            validateAndSaveData(metarResponseEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void validateAndSaveData(ResponseEntity<MetarReportResponse> response) {
        if (Objects.requireNonNull(response.getBody()).getData().size() > 0) {
            try {
                response.getBody().getData()
//                        .stream()
//                        .filter(metar -> Objects.equals(metar.getMetarType(), AircraftType.METER.name()))
                        .forEach(this::extractAndIngestReport);
            } catch (Exception e) {
                log.error("Exception while Ingesting the Metar report data. error message {}", e.getMessage());
            }
        } else {
            log.error("Metar Report data is empty");
        }
    }

    private void extractAndIngestReport(Metar metar) {
        metar.setAircraft(extractAndSetAircraft(metar));
        metar.setAircraftCondition(extractAndSetAircraftConditionType(metar));
        metarRepository.save(metar);
        log.info("Successfully saved Metar: {}", metar.getRawText());
    }

    private Aircraft extractAndSetAircraft(Metar metar) {
        Aircraft aircraft = new Aircraft();
        aircraft.setCode(extractCodeFromRawText(metar.getRawText()));
        aircraft.setType(metar.getMetarType());
        return aircraft;
    }

    private String extractCodeFromRawText(final String rawText) {
        return rawText.substring(1, 4);
    }

    private MultiValueMap<String, String> getMetarRequestParam() {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("dataSource", ReportType.METAR.getReportType());
        requestParam.add("requestType", "retrieve");
        requestParam.add("format", "xml");
        /* retrieve Metar data last 5min */
        requestParam.add("hoursBeforeNow", "0.10");
        return requestParam;
    }

    private AircraftCondition extractAndSetAircraftConditionType(Metar metar) {
        AircraftCondition aircraftCondition = new AircraftCondition();
        aircraftCondition.setIsSkyType(setConditionType(metar.getSkyConditions()));
        aircraftCondition.setIsQualityControlFlagType(setConditionType(metar.getQualityControlFlags()));
        return aircraftCondition;
    }

    private <T> Boolean setConditionType(List<T> conditionType) {
        return conditionType != null && conditionType.size() > 0;
    }

    //    manually trigger and save data into database
    public ResponseEntity<MetarReportResponse> updateMetarsFromAddsServer(String station, ZonedDateTime startTime, ZonedDateTime endTime) {
        try {
            ResponseEntity<MetarReportResponse> metarResponseEntity = noaaaddsService.getAircraftReportFromAddsServer(MetarReportResponse.class, ReportType.METAR.getReportType(), getMetarRequestParams(station, startTime, endTime));
            validateAndSaveData(metarResponseEntity);
            return metarResponseEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<MetarReportResponse> getMetarsFromAddsServer(String station, ZonedDateTime startTime, ZonedDateTime endTime) {
        try {
            ResponseEntity<MetarReportResponse> metarResponseEntity = noaaaddsService.getAircraftReportFromAddsServer(MetarReportResponse.class, ReportType.METAR.getReportType(), getMetarRequestParams(station, startTime, endTime));
            return metarResponseEntity;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private MultiValueMap<String, String> getMetarRequestParams(final String station, final ZonedDateTime startTime, final ZonedDateTime endTime) {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("dataSource", ReportType.METAR.getReportType());
        requestParam.add("requestType", "retrieve");
        requestParam.add("format", "xml");
        requestParam.add("stationString", station);
        requestParam.add("startTime", startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")));
        requestParam.add("endTime", endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")));

        return requestParam;
    }
}
