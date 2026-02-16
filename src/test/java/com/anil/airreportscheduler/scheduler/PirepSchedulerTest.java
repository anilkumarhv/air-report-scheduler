package com.anil.airreportscheduler.scheduler;

import com.anil.airreportscheduler.service.MetarService;
import com.anil.airreportscheduler.service.PirepService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PirepSchedulerTest {

    @Mock
    private PirepService pirepService;

    @Mock
    private MetarService metarService;

    @InjectMocks
    private PirepScheduler pirepScheduler;

    @Test
    void testPirepSchedulerTask_Success() {
        doNothing().when(pirepService).getAircraftReportFromAddsServer();

        pirepScheduler.pirepSchedulerTask();

        verify(pirepService, times(1)).getAircraftReportFromAddsServer();
    }

    @Test
    void testPirepSchedulerTask_Exception() {
        doThrow(new RuntimeException("API error")).when(pirepService).getAircraftReportFromAddsServer();

        assertDoesNotThrow(() -> pirepScheduler.pirepSchedulerTask());
    }

    @Test
    void testMetarSchedulerTask_Success() {
        doNothing().when(metarService).getMetarFromAddsServer();

        pirepScheduler.metarSchedulerTask();

        verify(metarService, times(1)).getMetarFromAddsServer();
    }

    @Test
    void testMetarSchedulerTask_Exception() {
        doThrow(new RuntimeException("API error")).when(metarService).getMetarFromAddsServer();

        assertDoesNotThrow(() -> pirepScheduler.metarSchedulerTask());
    }
}
