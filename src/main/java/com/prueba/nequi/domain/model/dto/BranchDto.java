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
@Schema(description = "Modelo de estrucutra de una sucursales")
public class BranchDto {

    @JsonProperty("id")
    @Schema(description = "Id de la sucursal", example = "1")
    private Integer id;

    @JsonProperty("nombre")
    @Schema(description = "Nombre de la sucursal", example = "1")
    private String name;

    @JsonProperty("franquiciaId")
    @Schema(description = "Id de la franquicia asociada", example = "1")
    private Integer franchiseId;

}
