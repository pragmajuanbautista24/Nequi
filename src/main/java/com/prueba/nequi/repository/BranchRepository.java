package com.prueba.nequi.repository;

import com.prueba.nequi.entity.Branch;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface BranchRepository extends R2dbcRepository<Branch, Integer> {
}
