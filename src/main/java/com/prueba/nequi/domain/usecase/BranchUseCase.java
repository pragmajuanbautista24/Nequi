package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.dto.BranchDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.entity.Branch;
import com.prueba.nequi.providers.mapper.BranchMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class BranchUseCase {

    private final BranchRepository repository;

    private final FranchiseRepository franchiseRepository;

    private final BranchMapper mapper;

    public BranchUseCase(BranchRepository repository, FranchiseRepository franchiseRepository, BranchMapper mapper) {
        this.repository = repository;
        this.franchiseRepository = franchiseRepository;
        this.mapper = mapper;
    }

    public Flux<BranchDto> findAllBranchs() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<BasicResponse<BranchDto>> findByIdBranch(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Sucursal existente: {}", entity);
                            BasicResponse<BranchDto> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(mapper.toDto(entity));
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<BranchDto> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<BranchDto>> saveBranch(BranchDto branch) {
        if (!validateFields(branch)) {
            BasicResponse<BranchDto> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return franchiseRepository.findById(branch.getFranchiseId())
                .flatMap(franchise -> repository.save(mapper.toEntity(branch))
                        .flatMap(entity -> {
                            log.info("Sucursal creada: {}", entity);
                            BasicResponse<BranchDto> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro creado"));
                            response.setPayload(mapper.toDto(entity));
                            return Mono.just(response);
                        }))
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<BranchDto> response = new BasicResponse();
                    response.setHeader(new Header("400", "Franquicia invalida para la sucursal"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<BranchDto>> updateBranch(Integer id, BranchDto branch) {
        if (Objects.isNull(id)) {
            BasicResponse<BranchDto> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return repository.findById(id)
                .flatMap(entity -> {
                    log.info("Sucursal existente: {}", entity);
                    entity.setName(branch.getName());
                    return repository.save(entity)
                            .flatMap(updateEntity -> {
                                log.info("Sucursal actualizada: {}", updateEntity);
                                BasicResponse<BranchDto> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(mapper.toDto(updateEntity));
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<BranchDto> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<BranchDto>> deleteByIdBranch(Integer id) {
        BasicResponse<BranchDto> response = new BasicResponse<>();
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

    private boolean validateFields(BranchDto branch) {
        return StringUtils.isNoneBlank(branch.getName())
                && Objects.nonNull(branch.getFranchiseId());
    }

}
