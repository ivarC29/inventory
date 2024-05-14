package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @Column("product_id")
    private Long                    productId;

    @Column("category_id")
    private Long                    categoryId;

    @Column("measure_id")
    private Long                    measureId;

    @Column("laboratory_id")
    private Long                    laboratoryId;

    @Column("product_name")
    private String                  productName;

    @Column("description")
    private String                  productDescription;

    @Column("product_barcode")
    private String                  productBarcode;

    @Column("product_cost")
    private Double                  productCost;

    @Column("product_price")
    private Double                  productPrice;

    @Column("quantity")
    private Double                 quantity;
}
