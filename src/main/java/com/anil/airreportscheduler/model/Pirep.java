package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
    private String windDirDegrees;
    @JacksonXmlProperty(localName = "wind_peed_kt")
    private Integer windSpeedKt;
    @JacksonXmlProperty(localName = "report_type")
    private String reportType;
    @Column(name = "raw_text", length = 5120)
    @JacksonXmlProperty(localName = "raw_text")
    private String rawText;
    @JacksonXmlProperty(localName = "visibility_statute_mi")
    private String visibilityStatuteMi;
    @JacksonXmlProperty(localName = "wx_tring")
    private String wxString;
    @JacksonXmlProperty(localName = "vert_gust_kt")
    private String vertGustKt;

    @Embedded
    private AircraftCondition aircraftCondition;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "quality_control_flags")
    @XmlElement(name = "quality_control_flags")
    private List<QualityControlFlags> qualityControlFlags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "icing_condition")
    @XmlElement(name = "icing_condition")
    private List<IcingCondition> icingConditions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sky_condition")
    @XmlElement(name = "sky_condition")
    private List<SkyCondition> skyConditions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pirep", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "turbulence_condition")
    @XmlElement(name = "turbulence_condition")
    private List<TurbulenceCondition> turbulenceConditions = new ArrayList<>();

    public void setSkyConditions(List<SkyCondition> skyConditions) {
        this.skyConditions = skyConditions;
        if (Objects.requireNonNull(skyConditions).size() > 0) {
            for (SkyCondition skyCondition : skyConditions) {
                skyCondition.setPirep(this);
            }
        }
    }

    public void setQualityControlFlags(List<QualityControlFlags> qualityControlFlags) {
        this.qualityControlFlags = qualityControlFlags;
        if (Objects.requireNonNull(qualityControlFlags).size() > 0) {
            for (QualityControlFlags qualityControlFlag : qualityControlFlags) {
                qualityControlFlag.setPirep(this);
            }
        }
    }

    public void setIcingConditions(List<IcingCondition> icingConditions) {
        this.icingConditions = icingConditions;
        if (Objects.requireNonNull(icingConditions).size() > 0) {
            for (IcingCondition icingCondition : icingConditions) {
                icingCondition.setPirep(this);
            }
        }
    }

    public void setTurbulenceConditions(List<TurbulenceCondition> turbulenceConditions) {
        this.turbulenceConditions = turbulenceConditions;
        if (Objects.requireNonNull(turbulenceConditions).size() > 0) {
            for (TurbulenceCondition turbulenceCondition : turbulenceConditions) {
                turbulenceCondition.setPirep(this);
            }
        }
    }
}
