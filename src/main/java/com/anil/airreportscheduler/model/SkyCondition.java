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
@Table(name = "sky_condition")
@JacksonXmlRootElement(localName = "sky_condition")
public class SkyCondition implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sky_condition_GEN")
    @SequenceGenerator(name = "sky_condition_GEN", sequenceName = "sky_condition_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JacksonXmlProperty(localName = "sky_cover", isAttribute = true)
    private String skyCover;
    @JacksonXmlProperty(localName = "cloud_base_ft_agl", isAttribute = true)
    private Integer cloudBaseFtAgl;
    @JacksonXmlProperty(localName = "cloud_top_ft_msl", isAttribute = true)
    private Integer cloudTopFtMsl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    private Pirep pirep;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metar_id")
    private Metar metar;
}
