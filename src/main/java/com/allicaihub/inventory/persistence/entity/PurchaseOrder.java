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
@Table("purchase_order")
public class PurchaseOrder {

    @Id
    @Column("order_id")
    private Long                    orderId;

    @Column("supplier_id")
    private Long                    supplierId;

    @Column("order_date")
    private LocalDate               orderDate;

    @Column("order_status")
    private OrderStatus             orderStatus;

}
