package com.allicaihub.inventory.persistence.repository;

import com.allicaihub.inventory.persistence.entity.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CategoryRepository extends R2dbcRepository<Category, Long> {
}
