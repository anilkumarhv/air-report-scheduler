package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
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
