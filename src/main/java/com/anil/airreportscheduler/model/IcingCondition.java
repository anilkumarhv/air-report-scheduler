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
@Table(name = "icing_condition")
@JacksonXmlRootElement(localName = "icing_condition")
public class IcingCondition implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "icing_condition_GEN")
    @SequenceGenerator(name = "icing_condition_GEN", sequenceName = "icing_condition_SEQ", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JacksonXmlProperty(localName = "icing_type", isAttribute = true)
    private String icingType;
    @JacksonXmlProperty(localName = "icing_intensity", isAttribute = true)
    private String icingIntensity;
    @JacksonXmlProperty(localName = "icing_base_ft_msl", isAttribute = true)
    private Integer icingBaseFtMsl;
    @JacksonXmlProperty(localName = "icing_top_ft_msl", isAttribute = true)
    private Integer icingTopFtMsl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pirep_id")
    private Pirep pirep;
}
