package com.anil.airreportscheduler.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class Aircraft {
    String code;
    String type;
}
