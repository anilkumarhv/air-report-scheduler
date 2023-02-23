package com.anil.airreportscheduler.model;

public enum ReportType {
    METAR("metars"),
    TAF("tafs"),
    AIRCRAFT("aircraftreports");

    private final String reportType;

    ReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }
}
