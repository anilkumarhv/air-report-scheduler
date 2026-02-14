package com.anil.airreportscheduler.service;

import com.anil.airreportscheduler.model.Metar;
import com.anil.airreportscheduler.model.MetarReportResponse;
import com.anil.airreportscheduler.repository.MetarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetarServiceTest {

    @Mock
    private NOAAADDSService noaaaddsService;

    @Mock
    private MetarRepository metarRepository;

    @InjectMocks
    private MetarService metarService;

    @Test
    void testGetMetarFromAddsServer_Success() {
        MetarReportResponse mockResponse = new MetarReportResponse();
        List<Metar> metars = new ArrayList<>();
        Metar metar = new Metar();
        metar.setRawText("METAR KJFK 121251Z");
        metar.setStationId("KJFK");
        metar.setMetarType("METAR");
        metars.add(metar);
        mockResponse.setData(metars);

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        when(metarRepository.save(any(Metar.class))).thenReturn(metar);

        metarService.getMetarFromAddsServer();

        verify(noaaaddsService, times(1)).getAircraftReportFromAddsServer(any(), any(), any());
        verify(metarRepository, times(1)).save(any(Metar.class));
    }

    @Test
    void testGetMetarFromAddsServer_EmptyData() {
        MetarReportResponse mockResponse = new MetarReportResponse();
        mockResponse.setData(new ArrayList<>());

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        metarService.getMetarFromAddsServer();

        verify(metarRepository, never()).save(any());
    }

    @Test
    void testGetMetarFromAddsServer_ExceptionHandled() {
        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenThrow(new RuntimeException("API error"));

        assertDoesNotThrow(() -> metarService.getMetarFromAddsServer());
    }

    @Test
    void testUpdateMetarsFromAddsServer_Success() {
        MetarReportResponse mockResponse = new MetarReportResponse();
        List<Metar> metars = new ArrayList<>();
        Metar metar = new Metar();
        metar.setRawText("METAR KJFK 121251Z");
        metar.setStationId("KJFK");
        metar.setMetarType("METAR");
        metars.add(metar);
        mockResponse.setData(metars);

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
        when(metarRepository.save(any(Metar.class))).thenReturn(metar);

        ResponseEntity<MetarReportResponse> result = metarService.updateMetarsFromAddsServer(
            "KJFK", ZonedDateTime.now().minusHours(1), ZonedDateTime.now());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetMetarsFromAddsServer_Success() {
        MetarReportResponse mockResponse = new MetarReportResponse();
        mockResponse.setData(new ArrayList<>());

        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        ResponseEntity<MetarReportResponse> result = metarService.getMetarsFromAddsServer(
            "KJFK", ZonedDateTime.now().minusHours(1), ZonedDateTime.now());

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testUpdateMetarsFromAddsServer_Exception() {
        when(noaaaddsService.getAircraftReportFromAddsServer(any(), any(), any()))
            .thenThrow(new RuntimeException("API error"));

        assertThrows(RuntimeException.class, () ->
            metarService.updateMetarsFromAddsServer("KJFK", ZonedDateTime.now().minusHours(1), ZonedDateTime.now())
        );
    }
}
