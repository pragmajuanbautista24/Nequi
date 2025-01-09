package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.entity.Franchise;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class FranchiseUseCase {

    private final FranchiseRepository repository;

    public FranchiseUseCase(FranchiseRepository repository) {
        this.repository = repository;
    }

    public Flux<Franchise> findAllFranchises() {
        return repository.findAll();
    }

    public Mono<BasicResponse<Franchise>> findByIdFranchise(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Franquicia existente: {}", entity);
                            BasicResponse<Franchise> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(entity);
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<Franchise> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<Franchise>> saveFranchise(Franchise franchise) {
        if (!validateFields(franchise)) {
            BasicResponse<Franchise> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return repository.save(franchise)
                .flatMap(entity -> {
                    log.info("Franquicia creada: {}", entity);
                    BasicResponse<Franchise> response = new BasicResponse();
                    response.setHeader(new Header("200", "Registro creado"));
                    response.setPayload(entity);
                    return Mono.just(response);
                });
    }

    public Mono<BasicResponse<Franchise>> updateFranchise(Integer id, Franchise franquicia) {
        if (Objects.isNull(id)) {
            BasicResponse<Franchise> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return repository.findById(id)
                .flatMap(entity -> {
                    log.info("Franquicia existente: {}", entity);
                    entity.setName(franquicia.getName());
                    return repository.save(entity)
                            .flatMap(updateEntity -> {
                                log.info("Franquicia actualizada: {}", updateEntity);
                                BasicResponse<Franchise> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(updateEntity);
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<Franchise> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<Franchise>> deleteByIdFranchise(Integer id) {
        BasicResponse<Franchise> response = new BasicResponse<>();
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

    private boolean validateFields(Franchise franchise) {
        return StringUtils.isNoneBlank(franchise.getName());
    }

}
