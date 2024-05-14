package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("unit_of_measure")
public class UnitOfMeasure {

    @Id
    @Column("measure_id")
    private Long            measureId;

    @Column("measure_name")
    private String          measureName;

    @Column("measure_abbreviation")
    private String          measureAbbreviation;

    @Column("measure_description")
    private String          measureDescription;

}
