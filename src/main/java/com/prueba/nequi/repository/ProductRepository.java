package com.prueba.nequi.repository;

import com.prueba.nequi.entity.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductRepository extends R2dbcRepository<Product, Integer> {
}
