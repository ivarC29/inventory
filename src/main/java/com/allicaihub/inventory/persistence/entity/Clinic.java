package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clinic {

    @Id
    @Column("clinic_id")
    private Long                clinicId;

    @Column("clinic_name")
    private String              clinicName;

    @Column("clinic_ruc")
    private String              clinicRuc;

    @Column("clinic_address")
    private String              clinicAddress;

    @Column("clinic_city")
    private String              clinicCity;

    @Column("clinic_state")
    private String              clinicState;

    @Column("clinic_zip")
    private String              clinicZip;

    @Column("clinic_country")
    private String              clinicCountry;

    @Column("clinic_cellphone")
    private String              clinicCellphone;

    @Column("clinic_email")
    private String              clinicEmail;

}
