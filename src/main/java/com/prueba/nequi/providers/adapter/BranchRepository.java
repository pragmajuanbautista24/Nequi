package com.prueba.nequi.providers.adapter;

import com.prueba.nequi.providers.entity.Branch;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository extends R2dbcRepository<Branch, Integer> {

    Flux<Branch> findAllByFranchiseId(Integer franchiseId);

}
