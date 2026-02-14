package com.anil.airreportscheduler.controller;

import com.anil.airreportscheduler.config.SecurityConfig;
import com.anil.airreportscheduler.model.Metar;
import com.anil.airreportscheduler.model.MetarReportResponse;
import com.anil.airreportscheduler.service.MetarService;
import com.anil.airreportscheduler.service.PirepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AviationController.class)
@Import(SecurityConfig.class)
class AviationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MetarService metarService;

    @MockitoBean
    private PirepService pirepService;

    @Test
    void testGetMetars_WithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/dataserver/aviation/metars")
                .param("station", "KJFK")
                .param("startTime", "2024-01-01T00:00:00Z")
                .param("endTime", "2024-01-02T00:00:00Z"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetMetars_WithAuthentication() throws Exception {
        MetarReportResponse mockResponse = new MetarReportResponse();
        List<Metar> metars = new ArrayList<>();
        Metar metar = new Metar();
        metar.setRawText("METAR KJFK 121251Z");
        metars.add(metar);
        mockResponse.setData(metars);

        when(metarService.getMetarsFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        mockMvc.perform(get("/api/dataserver/aviation/metars")
                .param("station", "KJFK")
                .param("startTime", "2024-01-01T00:00:00Z")
                .param("endTime", "2024-01-02T00:00:00Z"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testPostMetars_WithAuthentication() throws Exception {
        MetarReportResponse mockResponse = new MetarReportResponse();
        mockResponse.setData(new ArrayList<>());

        when(metarService.updateMetarsFromAddsServer(any(), any(), any()))
            .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        mockMvc.perform(post("/api/dataserver/aviation/metars")
                .param("station", "KJFK")
                .param("startTime", "2024-01-01T00:00:00Z")
                .param("endTime", "2024-01-02T00:00:00Z"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetMetars_InvalidStationCode() throws Exception {
        mockMvc.perform(get("/api/dataserver/aviation/metars")
                .param("station", "123")
                .param("startTime", "2024-01-01T00:00:00Z")
                .param("endTime", "2024-01-02T00:00:00Z"))
            .andExpect(status().isBadRequest());
    }
}
