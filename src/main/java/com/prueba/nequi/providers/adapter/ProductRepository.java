package com.prueba.nequi.providers.adapter;

import com.prueba.nequi.domain.model.dto.ProductBranchDto;
import com.prueba.nequi.providers.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends R2dbcRepository<Product, Integer> {

    Flux<Product> findAllByBranchId(Integer branchId);

    @Query("SELECT " +
            "f.nombre AS franchise, " +
            "s.nombre AS branch, " +
            "p.nombre AS product " +
            //"p.cantidad AS stock " +
            "FROM franquicia f " +
            "JOIN sucursal s ON f.id = s.franquicia_id " +
            "JOIN producto p ON s.id = p.sucursal_id " +
            "WHERE f.nombre = :nameFranchise " +
            "AND p.cantidad = " +
            "(SELECT MAX(p1.cantidad) " +
            "FROM producto p1 " +
            "WHERE p1.sucursal_id = s.id) " +
            "ORDER BY s.nombre")
    Flux<ProductBranchDto> findTopProductsByFranchise(String nameFranchise);

}
