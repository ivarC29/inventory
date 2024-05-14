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
@Table("clinic_inventory")
public class ClinicInventory {

    @Id
    @Column("inventory_id")
    private Long                    inventoryId;

    @Column("product_id")
    private Long                    productId;

    @Column("location_id")
    private Long                    locationId;

    @Column("quantity_in_stock")
    private Double                 quantityInStock;

    @Column("location_in_warehouse")
    private String                  locationInWarehouse;

}
