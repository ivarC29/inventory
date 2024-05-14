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
@Table("warehouse_location")
public class WarehouseLocation {

    @Id
    @Column("location_id")
    private Long                    locationId;

    @Column("clinic_id")
    private Long                    clinicId;

    @Column("location_name")
    private String                  locationName;

    @Column("location_address")
    private String                  locationAddress;

    @Column("location_capacity")
    private Integer                 locationCapacity;

}
