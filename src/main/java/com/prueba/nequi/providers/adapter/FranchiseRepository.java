package com.prueba.nequi.providers.adapter;

import com.prueba.nequi.providers.entity.Franchise;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends R2dbcRepository<Franchise, Integer> {

}
