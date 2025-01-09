package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.dto.ProductBranchDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.ProductRepository;
import com.prueba.nequi.providers.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class ProductUseCase {

    private final ProductRepository repository;

    private final BranchRepository branchRepository;

    private final DatabaseClient databaseClient;

    public ProductUseCase(ProductRepository repository, BranchRepository branchRepository, DatabaseClient databaseClient) {
        this.repository = repository;
        this.branchRepository = branchRepository;
        this.databaseClient = databaseClient;
    }

    public Flux<Product> findAllProducts() {
        return repository.findAll();
    }

    public Mono<BasicResponse<Product>> findByIdProduct(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Producto existente: {}", entity);
                            BasicResponse<Product> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(entity);
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<Product> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<Product>> saveProduct(Product product) {
        if (!validateFields(product)) {
            BasicResponse<Product> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return branchRepository.findById(product.getBranchId())
                .flatMap(branch -> repository.save(product)
                        .flatMap(entity -> {
                            log.info("Sucursal creada: {}", entity);
                            BasicResponse<Product> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro creado"));
                            response.setPayload(entity);
                            return Mono.just(response);
                        }))
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<Product> response = new BasicResponse();
                    response.setHeader(new Header("400", "Sucursal invalida para la sucursal"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<Product>> updateProduct(Integer id, Product product) {
        if (Objects.isNull(id)) {
            BasicResponse<Product> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return repository.findById(id)
                .flatMap(entity -> {
                    log.info("Producto existente: {}", entity);
                    entity.setName(Objects.nonNull(product.getName()) ? product.getName() : entity.getName());
                    entity.setStock(Objects.nonNull(product.getStock()) ? product.getStock() : entity.getStock());
                    return repository.save(entity)
                            .flatMap(updateEntity -> {
                                log.info("Producto actualizada: {}", updateEntity);
                                BasicResponse<Product> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(updateEntity);
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<Product> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<Product>> deleteByIdProduct(Integer id) {
        BasicResponse<Product> response = new BasicResponse<>();
        response.setHeader(new Header("400", "Registro no existe"));

        return repository.findById(id)
                .flatMap(entityFind ->
                        repository.deleteById(id)
                                .then(Mono.fromCallable(() -> {
                                    response.setHeader(new Header("200", "Registro eliminado exitosamente"));
                                    return response;
                                }))
                )
                .defaultIfEmpty(response);
    }

    public Flux<ProductBranchDto> getTopProducts(String franchiseName) {
        return repository.findTopProductsByFranchise(franchiseName);
    }

    private boolean validateFields(Product product) {
        return StringUtils.isNoneBlank(product.getName())
                && Objects.nonNull(product.getStock())
                && Objects.nonNull(product.getBranchId());
    }

}
