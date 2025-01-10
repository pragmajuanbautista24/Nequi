package com.prueba.nequi.domain.model.constants;

public enum Entryendpoints {

    FRANCHISE("/franquicias"),
    BRANCH("/sucursales"),
    PRODUCT("/productos");

    public final String url;

    Entryendpoints(String url) {
        this.url = url;
    }
}
