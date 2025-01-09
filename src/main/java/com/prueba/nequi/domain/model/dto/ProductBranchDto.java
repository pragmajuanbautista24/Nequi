package com.prueba.nequi.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBranchDto {

    private String franchise;
    private String branch;
    private String product;
    //private String stock;

}
