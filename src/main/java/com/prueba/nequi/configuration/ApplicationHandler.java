package com.prueba.nequi.configuration;

import com.prueba.nequi.domain.model.dto.BranchDto;
import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.domain.model.dto.ProductDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.BranchUseCase;
import com.prueba.nequi.domain.usecase.FranchiseUseCase;
import com.prueba.nequi.domain.usecase.ProductUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ApplicationHandler {

    private final FranchiseUseCase franchiseUseCase;

    private final BranchUseCase branchUseCase;

    private final ProductUseCase productUseCase;

    public ApplicationHandler(FranchiseUseCase franchiseUseCase, BranchUseCase branchUseCase, ProductUseCase productUseCase) {
        this.franchiseUseCase = franchiseUseCase;
        this.branchUseCase = branchUseCase;
        this.productUseCase = productUseCase;
    }

    public Mono<ServerResponse> franchiseCreate(ServerRequest request) {
        Mono<FranchiseDto> franchiseMono = request.bodyToMono(FranchiseDto.class);
        Mono<BasicResponse<FranchiseDto>> response = franchiseMono.flatMap(franchiseUseCase::saveFranchise);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> franchiseUpdate(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<FranchiseDto> franchiseMono = request.bodyToMono(FranchiseDto.class);
        Mono<BasicResponse<FranchiseDto>> response = franchiseMono.flatMap(franquicia -> franchiseUseCase.updateFranchise(id, franquicia));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> franchiseDelete(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<BasicResponse<FranchiseDto>> response = franchiseUseCase.deleteByIdFranchise(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> branchCreate(ServerRequest request) {
        Mono<BranchDto> branchMono = request.bodyToMono(BranchDto.class);
        Mono<BasicResponse<BranchDto>> response = branchMono.flatMap(branchUseCase::saveBranch);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> branchUpdate(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<BranchDto> branchMono = request.bodyToMono(BranchDto.class);
        Mono<BasicResponse<BranchDto>> response = branchMono.flatMap(franquicia -> branchUseCase.updateBranch(id, franquicia));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> branchDelete(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<BasicResponse<BranchDto>> response = branchUseCase.deleteByIdBranch(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> productCreate(ServerRequest request) {
        Mono<ProductDto> productMono = request.bodyToMono(ProductDto.class);
        Mono<BasicResponse<ProductDto>> response = productMono.flatMap(productUseCase::saveProduct);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> productUpdate(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<ProductDto> productMono = request.bodyToMono(ProductDto.class);
        Mono<BasicResponse<ProductDto>> response = productMono.flatMap(franquicia -> productUseCase.updateProduct(id, franquicia));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

    public Mono<ServerResponse> productDelete(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        Mono<BasicResponse<ProductDto>> response = productUseCase.deleteByIdProduct(id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, BasicResponse.class);
    }

}
