package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Laboratory {

    @Column("laboratory_id")
    private Long                laboratoryId;

    @Column("laboratory_name")
    private String              laboratoryName;

}
