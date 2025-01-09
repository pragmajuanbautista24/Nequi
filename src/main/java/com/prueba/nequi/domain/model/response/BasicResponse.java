package com.prueba.nequi.domain.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicResponse<T> {

    private Header header;

    private T payload;

}
