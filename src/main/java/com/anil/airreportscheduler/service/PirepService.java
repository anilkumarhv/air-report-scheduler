package com.anil.airreportscheduler.service;

import com.anil.airreportscheduler.model.*;
import com.anil.airreportscheduler.repository.PirepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PirepService {

    private final NOAAADDSService noaaaddsService;

    private final PirepRepository pirepRepository;

    public PirepService(NOAAADDSService noaaaddsService, PirepRepository pirepRepository) {
        this.noaaaddsService = noaaaddsService;
        this.pirepRepository = pirepRepository;
    }

    public void getAircraftReportFromAddsServer() {
        try {
            ResponseEntity<AircraftReportResponse> pirepResponseEntity = noaaaddsService.getAircraftReportFromAddsServer(AircraftReportResponse.class, ReportType.AIRCRAFT.getReportType(), getPirepRequestParam());
            validateAndSaveData(pirepResponseEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void validateAndSaveData(ResponseEntity<AircraftReportResponse> response) {
        if (Objects.requireNonNull(response.getBody()).getData().size() > 0) {
            try {
                response.getBody().getData().stream()
                        .filter(pirep -> !Objects.equals(pirep.getReportType(), AircraftType.AIREP.name()))
                        .forEach(this::extractAndIngestReport);
            } catch (Exception e) {
                log.error("Exception while Ingesting the Aircraft report data. error message {}", e.getMessage());
            }
        } else {
            log.error("Aircraft Report data is empty");
        }
    }

    private void extractAndIngestReport(Pirep pirep) {
        pirep.setAircraft(extractAndSetAircraft(pirep));
        pirep.setAircraftCondition(extractAndSetAircraftConditionType(pirep));
        pirepRepository.save(pirep);
        log.info("Successfully saved Pirep: {}", pirep.getRawText());
    }

    private Aircraft extractAndSetAircraft(Pirep pirep) {
        Aircraft aircraft = new Aircraft();
        aircraft.setCode(extractCodeFromRawText(pirep.getRawText()));
        aircraft.setType(pirep.getReportType());
        return aircraft;
    }

    private String extractCodeFromRawText(final String rawText) {
        return rawText.substring(0, 3);
    }

    private MultiValueMap<String, String> getPirepRequestParam() {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("dataSource", ReportType.AIRCRAFT.getReportType());
        requestParam.add("requestType", "retrieve");
        requestParam.add("format", "xml");
        requestParam.add("hoursBeforeNow", "0.20");
        return requestParam;
    }

    private AircraftCondition extractAndSetAircraftConditionType(Pirep pirep) {
        AircraftCondition aircraftCondition = new AircraftCondition();
        aircraftCondition.setIsIcingType(setConditionType(pirep.getIcingConditions()));
        aircraftCondition.setIsSkyType(setConditionType(pirep.getSkyConditions()));
        aircraftCondition.setIsTurbulenceType(setConditionType(pirep.getTurbulenceConditions()));
        aircraftCondition.setIsQualityControlFlagType(setConditionType(pirep.getQualityControlFlags()));
        return aircraftCondition;
    }

    private <T> Boolean setConditionType(List<T> conditionType) {
        return conditionType != null && conditionType.size() > 0;
    }
}
