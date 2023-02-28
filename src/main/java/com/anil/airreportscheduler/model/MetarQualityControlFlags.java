package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metar_quality_control_flags")
@JacksonXmlRootElement(localName = "quality_control_flags")
public class MetarQualityControlFlags {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metar_quality_control_flags_GEN")
    @SequenceGenerator(name = "metar_quality_control_flags_GEN", sequenceName = "metar_quality_control_flags_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @JacksonXmlProperty(localName = "corrected")
    private String corrected;
    @JacksonXmlProperty(localName = "auto")
    private String auto;
    @JacksonXmlProperty(localName = "auto_station")
    private String autoStation;
    @JacksonXmlProperty(localName = "maintenance_indicator_on")
    private String maintenanceIndicatorOn;
    @JacksonXmlProperty(localName = "no_signal")
    private String noSignal;
    @JacksonXmlProperty(localName = "lightning_sensor_off")
    private String lightningSensorOff;
    @JacksonXmlProperty(localName = "freezing_rain_sensor_off")
    private String freezingRainSensorOff;
    @JacksonXmlProperty(localName = "present_weather_sensor_off")
    private String presentWeatherSensorOff;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metar_id")
    private Metar metar;
}
