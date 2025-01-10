package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.dto.ProductBranchDto;
import com.prueba.nequi.domain.model.dto.ProductDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.ProductRepository;
import com.prueba.nequi.providers.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class ProductUseCase {

    private final ProductRepository repository;

    private final BranchRepository branchRepository;

    private final ProductMapper mapper;

    public ProductUseCase(ProductRepository repository, BranchRepository branchRepository, ProductMapper mapper) {
        this.repository = repository;
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    public Flux<ProductDto> findAllProducts() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<BasicResponse<ProductDto>> findByIdProduct(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Producto existente: {}", entity);
                            BasicResponse<ProductDto> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(mapper.toDto(entity));
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<ProductDto> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<ProductDto>> saveProduct(ProductDto product) {
        if (!validateFields(product)) {
            BasicResponse<ProductDto> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return branchRepository.findById(product.getBranchId())
                .flatMap(branch -> repository.save(mapper.toEntity(product))
                        .flatMap(entity -> {
                            log.info("Sucursal creada: {}", entity);
                            BasicResponse<ProductDto> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro creado"));
                            response.setPayload(mapper.toDto(entity));
                            return Mono.just(response);
                        }))
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<ProductDto> response = new BasicResponse();
                    response.setHeader(new Header("400", "Sucursal invalida para la sucursal"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<ProductDto>> updateProduct(Integer id, ProductDto product) {
        if (Objects.isNull(id)) {
            BasicResponse<ProductDto> response = new BasicResponse();
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
                                BasicResponse<ProductDto> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(mapper.toDto(updateEntity));
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<ProductDto> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<ProductDto>> deleteByIdProduct(Integer id) {
        BasicResponse<ProductDto> response = new BasicResponse<>();
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

    private boolean validateFields(ProductDto product) {
        return StringUtils.isNoneBlank(product.getName())
                && Objects.nonNull(product.getStock())
                && Objects.nonNull(product.getBranchId());
    }

}
