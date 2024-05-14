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
@Table("purchase_order_detail")
public class PurchaseOrderDetail {

    @Id
    @Column("order_detail_id")
    private Long                    orderDetailId;

    @Column("order_id")
    private Long                    orderId;

    @Column("product_id")
    private Long                    productId;

    @Column("quantity_ordered")
    private Double                  quantityOrdered;

    @Column("unit_price")
    private Double                  unitPrice;

    @Column("order_date")
    private LocalDate orderDate;

}
