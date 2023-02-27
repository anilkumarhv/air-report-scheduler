package com.anil.airreportscheduler.service;

import com.anil.airreportscheduler.model.Aircraft;
import com.anil.airreportscheduler.model.Metar;
import com.anil.airreportscheduler.model.MetarReportResponse;
import com.anil.airreportscheduler.model.ReportType;
import com.anil.airreportscheduler.repository.MetarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
        metarRepository.save(metar);
    }

    private Aircraft extractAndSetAircraft(Metar metar) {
        Aircraft aircraft = new Aircraft();
        aircraft.setCode(extractCodeFromRawText(metar.getRawText()));
        aircraft.setType(metar.getMetarType());
        return aircraft;
    }

    private String extractCodeFromRawText(final String rawText) {
        return rawText.substring(1, 3);
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
}
