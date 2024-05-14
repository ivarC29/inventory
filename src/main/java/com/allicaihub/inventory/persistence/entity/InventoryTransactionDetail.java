package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("inventory_transaction_detail")
public class InventoryTransactionDetail {

    @Id
    @Column("transaction_detail_id")
    private Long            transactionDetailId;

    @Column("transaction_id")
    private Long            transactionId;

    @Column("lot_id")
    private Long            lotId;

    @Column("quantity_affected")
    private Double          quantityAffected;

    @Column("transaction_date_time")
    private LocalDateTime transactionDateTime;

}
