package com.prueba.nequi.configuration;

import com.prueba.nequi.domain.model.constants.Entryendpoints;
import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ApplicationRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ApplicationHandler handler) {
        return RouterFunctions
                .route(POST(Entryendpoints.FRANCHISE.url), handler::franchiseCreate)
                .andRoute(PUT(Entryendpoints.FRANCHISE.url + "/{id}"), handler::franchiseUpdate)
                .andRoute(DELETE(Entryendpoints.FRANCHISE.url + "/{id}"), handler::franchiseDelete)
                .andRoute(POST(Entryendpoints.BRANCH.url), handler::branchCreate)
                .andRoute(PUT(Entryendpoints.BRANCH.url + "/{id}"), handler::branchUpdate)
                .andRoute(DELETE(Entryendpoints.BRANCH.url + "/{id}"), handler::branchDelete)
                .andRoute(POST(Entryendpoints.PRODUCT.url), handler::productCreate)
                .andRoute(PUT(Entryendpoints.PRODUCT.url + "/{id}"), handler::productUpdate)
                .andRoute(DELETE(Entryendpoints.PRODUCT.url + "/{id}"), handler::productDelete);

    }

}
