package com.prueba.nequi.providers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column("id")
    private Integer id;

    @Column("nombre")
    private String name;

    @Column("cantidad")
    private Integer stock;

    @Column("sucursal_id")
    private Integer branchId;

}
