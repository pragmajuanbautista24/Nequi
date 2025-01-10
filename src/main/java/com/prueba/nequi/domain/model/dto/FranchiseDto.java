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
@Schema(description = "Modelo de estrucutra de una franquicia")
public class FranchiseDto {

    @JsonProperty("id")
    @Schema(description = "Id de la franquicia", example = "1")
    private Integer id;

    @JsonProperty("nombre")
    @Schema(description = "Nombre de la franquicia", example = "Nequi Colombia")
    private String name;

}
