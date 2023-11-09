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
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metar", indexes = {
        @Index(name = "idx_metar_code_type", columnList = "code, type")
})
@XmlRootElement(name = "METAR")
@XmlAccessorType(XmlAccessType.NONE)
@JacksonXmlRootElement(localName = "METAR")
public class Metar implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metar_gen")
    @SequenceGenerator(name = "metar_gen", sequenceName = "metar_seq_gen", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private Aircraft aircraft;

    @Column(name = "raw_text", length = 5120)
    @JacksonXmlProperty(localName = "raw_text")
    private String rawText;
    @JacksonXmlProperty(localName = "station_id")
    private String stationId;
    @JacksonXmlProperty(localName = "observation_time")
    private ZonedDateTime observationTime;
    @JacksonXmlProperty(localName = "latitude")
    private Double latitude;
    @JacksonXmlProperty(localName = "longitude")
    private Double longitude;
    @JacksonXmlProperty(localName = "temp_c")
    private Double tempC;
    @JacksonXmlProperty(localName = "dewpoint_c")
    private Double dewpointC;
    @JacksonXmlProperty(localName = "wind_dir_degrees")
    private String windDirDegrees;
    @JacksonXmlProperty(localName = "wind_speed_kt")
    private Integer windSpeedKt;
    @JacksonXmlProperty(localName = "wind_gust_kt")
    private Integer windGustKt;
    @JacksonXmlProperty(localName = "visibility_statute_mi")
    private String visibilityStatuteMi;
    @JacksonXmlProperty(localName = "altim_in_hg")
    private Double altimInHg;
    @JacksonXmlProperty(localName = "sea_level_pressure_mb")
    private Double seaLevelPressureMb;
    @JacksonXmlProperty(localName = "wx_string")
    private String wxString;
    @JacksonXmlProperty(localName = "flight_category")
    private String flightCategory;
    @JacksonXmlProperty(localName = "three_hr_pressure_tendency_mb")
    private Double threeHrPressureTendencyMb;
    @JacksonXmlProperty(localName = "maxT_c")
    private Double maxTC;
    @JacksonXmlProperty(localName = "minT_c")
    private Double minTC;
    @JacksonXmlProperty(localName = "maxT24hr_c")
    private Double maxT24hrC;
    @JacksonXmlProperty(localName = "minT24hr_c")
    private Double minT24hrC;
    @JacksonXmlProperty(localName = "precip_in")
    private Double precipIn;
    @JacksonXmlProperty(localName = "pcp3hr_in")
    private Double pcp3hrIn;
    @JacksonXmlProperty(localName = "pcp6hr_in")
    private Double pcp6hrIn;
    @JacksonXmlProperty(localName = "pcp24hr_in")
    private Double pcp24hrIn;
    @JacksonXmlProperty(localName = "snow_in")
    private Double snowIn;
    @JacksonXmlProperty(localName = "vert_vis_ft")
    private Integer vertVisFt;
    @JacksonXmlProperty(localName = "metar_type")
    private String metarType;
    @JacksonXmlProperty(localName = "elevation_m")
    private Double elevationM;

    @Embedded
    private AircraftCondition aircraftCondition;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "quality_control_flags")
    @XmlElement(name = "quality_control_flags")
    private List<MetarQualityControlFlags> qualityControlFlags = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "metar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sky_condition")
    @XmlElement(name = "sky_condition")
    private List<SkyCondition> skyConditions = new ArrayList<>();

    public void setSkyConditions(List<SkyCondition> skyConditions) {
        this.skyConditions = skyConditions;
        if (Objects.requireNonNull(skyConditions).size() > 0) {
            for (SkyCondition skyCondition : skyConditions) {
                skyCondition.setMetar(this);
            }
        }
    }

    public void setQualityControlFlags(List<MetarQualityControlFlags> qualityControlFlags) {
        this.qualityControlFlags = qualityControlFlags;

        if (Objects.requireNonNull(qualityControlFlags).size() > 0) {
            for (MetarQualityControlFlags qualityControlFlag : qualityControlFlags) {
                qualityControlFlag.setMetar(this);
            }
        }
    }
}
