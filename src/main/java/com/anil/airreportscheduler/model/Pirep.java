package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pirep", indexes = {
        @Index(name = "idx_pirep_code_type", columnList = "code, type")
})
@XmlRootElement(name = "AircraftReport")
@XmlAccessorType(XmlAccessType.NONE)
@JacksonXmlRootElement(localName = "AircraftReport")
public class Pirep implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pirep_gen")
    @SequenceGenerator(name = "pirep_gen", sequenceName = "pirep_seq_gen", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private Aircraft aircraft;

    @JacksonXmlProperty(localName = "receipt_time")
    private ZonedDateTime receiptTime;
    @JacksonXmlProperty(localName = "observation_time")
    private ZonedDateTime observationTime;
    @JacksonXmlProperty(localName = "aircraft_ref")
    private String aircraftRef;
    @JacksonXmlProperty(localName = "latitude")
    private Double latitude;
    @JacksonXmlProperty(localName = "longitude")
    private Double longitude;
    @JacksonXmlProperty(localName = "altitude_ft_msl")
    private Integer altitudeFtMsl;
    @JacksonXmlProperty(localName = "temp_c")
    private Double tempC;
    @JacksonXmlProperty(localName = "wind_dir_degrees")
    private Integer windDirDegrees;
    @JacksonXmlProperty(localName = "wind_peed_kt")
    private Integer windSpeedKt;
    @JacksonXmlProperty(localName = "report_type")
    private String reportType;
    @Column(name = "raw_text", length = 5120)
    @JacksonXmlProperty(localName = "raw_text")
    private String rawText;
    @JacksonXmlProperty(localName = "visibility_statute_mi")
    private Integer visibilityStatuteMi;
    @JacksonXmlProperty(localName = "wx_tring")
    private String wxString;
    @JacksonXmlProperty(localName = "vert_gust_kt")
    private String vertGustKt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "quality_control_flags")
    @XmlElement(name = "quality_control_flags")
    private List<QualityControlFlags> qualityControlFlags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "icing_condition")
    @XmlElement(name = "icing_condition")
    private List<IcingCondition> icingConditions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sky_condition")
    @XmlElement(name = "sky_condition")
    private List<SkyCondition> skyConditions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "turbulence_condition")
    @XmlElement(name = "turbulence_condition")
    private List<TurbulenceCondition> turbulenceConditions;
}
