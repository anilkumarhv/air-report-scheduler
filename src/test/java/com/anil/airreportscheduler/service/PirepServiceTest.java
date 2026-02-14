package com.anil.airreportscheduler.service;

import com.anil.airreportscheduler.model.AircraftReportResponse;
import com.anil.airreportscheduler.model.Pirep;
import com.anil.airreportscheduler.repository.PirepRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PirepServiceTest {

    @Mock
    private NOAAADDSService noaaaddsService;

    @Mock
    private PirepRepository pirepRepository;

    @InjectMocks
    private PirepService pirepService;

    @Test
    void testGetAircraftReportFromAddsServer_Success() {
        AircraftReportResponse mockResponse = new AircraftReportResponse();
        List<Pirep> pireps = new ArrayList<>();
        Pirep pirep = new Pirep();
        pirep.setRawText("UA /OV KJFK");
        pirep.setReportType("PIREP");
        pireps.add(pirep);
        mockResponse.setData(pireps);

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        when(pirepRepository.save(any(Pirep.class))).thenReturn(pirep);

        pirepService.getAircraftReportFromAddsServer();

        verify(noaaaddsService, times(1)).getAircraftReportFromAddsServer(any(), any(), any());
        verify(pirepRepository, times(1)).save(any(Pirep.class));
    }

    @Test
    void testGetAircraftReportFromAddsServer_EmptyData() {
        AircraftReportResponse mockResponse = new AircraftReportResponse();
        mockResponse.setData(new ArrayList<>());

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        pirepService.getAircraftReportFromAddsServer();

        verify(pirepRepository, never()).save(any());
    }

    @Test
    void testGetAircraftReportFromAddsServer_FiltersAIREP() {
        AircraftReportResponse mockResponse = new AircraftReportResponse();
        List<Pirep> pireps = new ArrayList<>();
        Pirep airep = new Pirep();
        airep.setRawText("UA /OV KJFK");
        airep.setReportType("AIREP");
        pireps.add(airep);
        mockResponse.setData(pireps);

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        pirepService.getAircraftReportFromAddsServer();

        verify(pirepRepository, never()).save(any());
    }

    @Test
    void testGetAircraftReportFromAddsServer_ExceptionHandled() {
        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenThrow(new RuntimeException("API error"));

        assertDoesNotThrow(() -> pirepService.getAircraftReportFromAddsServer());
    }
}
