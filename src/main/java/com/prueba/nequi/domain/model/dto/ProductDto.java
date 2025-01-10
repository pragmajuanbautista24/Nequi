package com.prueba.nequi.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de estrucutra de un producto")
public class ProductDto {

    @JsonProperty("id")
    @Schema(description = "Id del producto", example = "1")
    private Integer id;

    @JsonProperty("nombre")
    @Schema(description = "Nombre del producto", example = "Cuenta de Ahorros")
    private String name;

    @JsonProperty("cantidad")
    @Schema(description = "Cantidad del producto", example = "1")
    private Integer stock;

    @JsonProperty("sucursalId")
    @Schema(description = "Id de la sucursal asociada", example = "1")
    private Integer branchId;

}
