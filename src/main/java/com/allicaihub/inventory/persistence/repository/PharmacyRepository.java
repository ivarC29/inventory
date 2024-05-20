package com.allicaihub.inventory.persistence.repository;

import com.allicaihub.inventory.persistence.entity.Pharmacy;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PharmacyRepository extends R2dbcRepository<Pharmacy, Long> {
}
