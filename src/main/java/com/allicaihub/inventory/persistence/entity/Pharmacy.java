package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pharmacy {

    @Id
    @Column("pharmacy_id")
    private Long                        pharmacyId;

    @Column("pharmacy_name")
    private String                      pharmacyName;

    @Column("pharmacy_ruc")
    private String                      pharmacyRuc;

    @Column("pharmacy_city")
    private String                      pharmacyCity;

    @Column("pharmacy_country")
    private String                      pharmacyCountry;

    @Column("pharmacy_email")
    private String                      pharmacyEmail;

    @Column("pharmacy_cellphone")
    private String                      pharmacyCellphone;

    @Column("pharmacy_address")
    private String                      pharmacyAddress;

    @Column("pharmacy_responsible")
    private String                      pharmacyResponsible;


}
