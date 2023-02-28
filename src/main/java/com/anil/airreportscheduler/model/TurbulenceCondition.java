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
@Table(name = "turbulence_condition")
@JacksonXmlRootElement(localName = "turbulence_condition")
public class TurbulenceCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turbulence_condition_GEN")
    @SequenceGenerator(name = "turbulence_condition_GEN", sequenceName = "turbulence_condition_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JacksonXmlProperty(localName = "turbulence_type", isAttribute = true)
    private String turbulenceType;
    @JacksonXmlProperty(localName = "turbulence_intensity", isAttribute = true)
    private String turbulenceIntensity;
    @JacksonXmlProperty(localName = "turbulence_base_ft_msl", isAttribute = true)
    private Integer turbulenceBaseFtMsl;
    @JacksonXmlProperty(localName = "turbulence_top_ft_msl", isAttribute = true)
    private Integer turbulenceTopFtMsl;
    @JacksonXmlProperty(localName = "turbulence_freq", isAttribute = true)
    private String turbulenceFreq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    private Pirep pirep;
}
