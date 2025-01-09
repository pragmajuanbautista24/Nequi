package com.prueba.nequi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("franquicia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {

    @Id
    @Column("id")
    private Integer id;

    @Column("nombre")
    private String name;

    @Column("sucursales")
    private String[] branches;

}
