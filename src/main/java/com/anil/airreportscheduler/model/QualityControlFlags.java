package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quality_control_flags")
@JacksonXmlRootElement(localName = "quality_control_flags")
public class QualityControlFlags implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quality_control_flags_GEN")
    @SequenceGenerator(name = "quality_control_flags_GEN", sequenceName = "quality_control_flags_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JacksonXmlProperty(localName = "mid_point_assumed")
    private String midPointAssumed;
    @JacksonXmlProperty(localName = "no_time_stamp")
    private String noTimeStamp;
    @JacksonXmlProperty(localName = "flt_lvl_range")
    private String fltLvlRange;
    @JacksonXmlProperty(localName = "above_ground_level_indicated")
    private String aboveGroundLevelIndicated;
    @JacksonXmlProperty(localName = "no_flt_lvl")
    private String noFltLvl;
    @JacksonXmlProperty(localName = "bad_location")
    private String badLocation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    private Pirep pirep;
}
