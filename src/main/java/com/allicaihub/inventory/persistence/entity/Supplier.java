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
public class Supplier {

    @Id
    @Column("supplier_id")
    private Long                supplierId;

    @Column("supplier_name")
    private String              supplierName;

    @Column("supplier_ruc")
    private String              supplierRuc;

    @Column("supplier_address")
    private String              supplierAddress;

    @Column("supplier_cellphone")
    private String              supplierCellPhone;

    @Column("supplier_email")
    private String              supplierEmail;

}
