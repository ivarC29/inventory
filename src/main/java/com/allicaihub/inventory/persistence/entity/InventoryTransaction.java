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
@Table("inventory_transaction")
public class InventoryTransaction {

    @Id
    @Column("transaction_id")
    private Long                        transactionId;

    @Column("supplier_id")
    private Long                        supplierId;

    @Column("inventory_id")
    private Long                        inventoryId;

    @Column("pharmacy_id")
    private Long                        pharmacyId;

    @Column("transaction_type")
    private TransactionType             type;


    @Column("transaction_date_time")
    private LocalDateTime               transactionDateTime;

}
