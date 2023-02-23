package com.anil.airreportscheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/* stores the basic information of the airport*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "station")
public class Station implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "station_seq_gen")
    private long id;
    private String city;
    private String country;
    private String elevationFt;
    private String elevationM;
    private String gps;
    private String iata;
    private String icao;
    private String latitude;
    private String local;
    private String longitude;
    private String name;
    private String note;
    private String reporting;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Runways> runways;
    private String state;
    private String type;
    private String website;
    private String wiki;
}
