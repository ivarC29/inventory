package com.allicaihub.inventory.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @Column("category_id")
    private Long                categoryId;

    @Column("category_name")
    private String              categoryName;

    @Column("category_description")
    private String              categoryDescription;
}
