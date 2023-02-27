package com.anil.airreportscheduler.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "response")
public class MetarReportResponse {

    @XmlElement(name = "METAR")
    private List<Metar> data;
}
