package com.prueba.nequi.repository;

import com.prueba.nequi.entity.Franchise;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FranchiseRepository extends R2dbcRepository<Franchise, Integer> {
}
