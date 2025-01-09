package com.prueba.nequi.domain.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Header {

    @JsonProperty("codigoRespuesta")
    private String responseCode;

    @JsonProperty("mensaje")
    private String message;

}
