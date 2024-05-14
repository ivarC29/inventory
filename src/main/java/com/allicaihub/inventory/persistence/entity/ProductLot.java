package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("product_lot")
public class ProductLot {

    @Id
    @Column("lot_id")
    private Long                    lotId;

    @Column("product_id")
    private Long                    productId;

    @Column("manufacturing_date")
    private LocalDate               manufacturingDate;

    @Column("expiration_date")
    private LocalDate               expirationDate;

    @Column("lot_number")
    private String                  lotNumber;// supplier id - sequence - date    example:  2403240508

    private Double                  quantity;

}
