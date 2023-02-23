package com.anil.airreportscheduler.model;

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
@Table(name = "runways")
public class Runways implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runway_seq_gen")
    private Long id;
    private Integer lengthFt;
    private Integer widthFt;
    private String surface;
    private Boolean lights;
    private String ident1;
    private String ident2;
    private Double bearing1;
    private Double bearing2;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;
}
