package com.anil.airreportscheduler.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NOAAADDSServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private NOAAADDSService noaaaddsService;

    private MultiValueMap<String, String> requestParams;

    @BeforeEach
    void setUp() {
        noaaaddsService = new NOAAADDSService(restTemplate, "https://test.api.com");
        requestParams = new LinkedMultiValueMap<>();
        requestParams.add("dataSource", "metars");
    }

    @Test
    void testGetAircraftReportFromAddsServer_Success() {
        String mockResponse = "<response>test</response>";
        ResponseEntity<String> mockEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(restTemplate.exchange(any(), eq(HttpMethod.GET), any(), eq(String.class)))
            .thenReturn(mockEntity);

        ResponseEntity<String> result = noaaaddsService.getAircraftReportFromAddsServer(
            String.class, "METAR", requestParams);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
        verify(restTemplate, times(1)).exchange(any(), any(), any(), eq(String.class));
    }

    @Test
    void testGetAircraftReportFromAddsServer_NonOkStatus() {
        String mockResponse = "<response>test</response>";
        ResponseEntity<String> mockEntity = new ResponseEntity<>(mockResponse, HttpStatus.ACCEPTED);
        when(restTemplate.exchange(any(), eq(HttpMethod.GET), any(), eq(String.class)))
            .thenReturn(mockEntity);

        ResponseEntity<String> result = noaaaddsService.getAircraftReportFromAddsServer(
            String.class, "METAR", requestParams);

        assertNotNull(result);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testGetAircraftReportFromAddsServer_ClientError() {
        when(restTemplate.exchange(any(), any(), any(), eq(String.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(RuntimeException.class, () ->
            noaaaddsService.getAircraftReportFromAddsServer(String.class, "METAR", requestParams)
        );
    }

    @Test
    void testGetAircraftReportFromAddsServer_ServerError() {
        when(restTemplate.exchange(any(), any(), any(), eq(String.class)))
            .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(RuntimeException.class, () ->
            noaaaddsService.getAircraftReportFromAddsServer(String.class, "METAR", requestParams)
        );
    }

    @Test
    void testGetAircraftReportFromAddsServer_NetworkError() {
        when(restTemplate.exchange(any(), any(), any(), eq(String.class)))
            .thenThrow(new ResourceAccessException("Connection refused"));

        assertThrows(RuntimeException.class, () ->
            noaaaddsService.getAircraftReportFromAddsServer(String.class, "METAR", requestParams)
        );
    }
}
