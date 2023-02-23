package com.anil.airreportscheduler.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
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
}
