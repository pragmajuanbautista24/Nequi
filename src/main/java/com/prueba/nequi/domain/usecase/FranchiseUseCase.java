package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.mapper.FranchiseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class FranchiseUseCase {

    private final FranchiseRepository repository;

    private final FranchiseMapper mapper;

    public FranchiseUseCase(FranchiseRepository repository, FranchiseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flux<FranchiseDto> findAllFranchises() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<BasicResponse<FranchiseDto>> findByIdFranchise(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Franquicia existente: {}", entity);
                            BasicResponse<FranchiseDto> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(mapper.toDto(entity));
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<FranchiseDto> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<FranchiseDto>> saveFranchise(FranchiseDto franchise) {
        if (!validateFields(franchise)) {
            BasicResponse<FranchiseDto> response = new BasicResponse();
                response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }

        return repository.save(mapper.toEntity(franchise))
                .flatMap(entity -> {
                    log.info("Franquicia creada: {}", entity);
                    BasicResponse<FranchiseDto> response = new BasicResponse();
                    response.setHeader(new Header("200", "Registro creado"));
                    response.setPayload(mapper.toDto(entity));
                    return Mono.just(response);
                });
    }

    public Mono<BasicResponse<FranchiseDto>> updateFranchise(Integer id, FranchiseDto franquicia) {
        if (Objects.isNull(id)) {
            BasicResponse<FranchiseDto> response = new BasicResponse();
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
                                BasicResponse<FranchiseDto> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(mapper.toDto(updateEntity));
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<FranchiseDto> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<FranchiseDto>> deleteByIdFranchise(Integer id) {
        BasicResponse<FranchiseDto> response = new BasicResponse<>();
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

    private boolean validateFields(FranchiseDto franchise) {
        return StringUtils.isNoneBlank(franchise.getName());
    }

}
