package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.model.response.Header;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.entity.Branch;
import com.prueba.nequi.providers.entity.Franchise;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
public class BranchUseCase {

    private final BranchRepository repository;

    private final FranchiseRepository franchiseRepository;

    public BranchUseCase(BranchRepository repository, FranchiseRepository franchiseRepository) {
        this.repository = repository;
        this.franchiseRepository = franchiseRepository;
    }

    public Flux<Branch> findAllBranchs() {
        return repository.findAll();
    }

    public Mono<BasicResponse<Branch>> findByIdBranch(Integer id) {
        return Mono.just(id)
                .flatMap(entityId -> repository.findById(entityId)
                        .flatMap(entity -> {
                            log.info("Sucursal existente: {}", entity);
                            BasicResponse<Branch> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro encontrado"));
                            response.setPayload(entity);
                            return Mono.just(response);
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                                    BasicResponse<Branch> response = new BasicResponse();
                                    response.setHeader(new Header("400", "Registro no encontrado"));
                                    return Mono.just(response);
                                })
                        ));
    }

    public Mono<BasicResponse<Branch>> saveBranch(Branch branch) {
        if (!validateFields(branch)) {
            BasicResponse<Branch> response = new BasicResponse();
            response.setHeader(new Header("400", "Parametros de entrada invalidos"));
            return Mono.just(response);
        }
        return franchiseRepository.findById(branch.getFranchiseId())
                .flatMap(franchise -> repository.save(branch)
                        .flatMap(entity -> {
                            log.info("Sucursal creada: {}", entity);
                            BasicResponse<Branch> response = new BasicResponse();
                            response.setHeader(new Header("200", "Registro creado"));
                            response.setPayload(entity);
                            return Mono.just(response);
                        }))
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<Branch> response = new BasicResponse();
                    response.setHeader(new Header("400", "Franquicia invalida para la sucursal"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<Branch>> updateBranch(Integer id, Branch branch) {
        if (Objects.isNull(id)) {
            BasicResponse<Branch> response = new BasicResponse();
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
                                BasicResponse<Branch> response = new BasicResponse();
                                response.setHeader(new Header("200", "Registro actualizado"));
                                response.setPayload(updateEntity);
                                return Mono.just(response);
                            });
                })
                .switchIfEmpty(Mono.defer(() -> {
                    BasicResponse<Branch> response = new BasicResponse();
                    response.setHeader(new Header("400", "Registro no existe"));
                    return Mono.just(response);
                }));
    }

    public Mono<BasicResponse<Branch>> deleteByIdBranch(Integer id) {
        BasicResponse<Branch> response = new BasicResponse<>();
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

    private boolean validateFields(Branch branch) {
        return StringUtils.isNoneBlank(branch.getName())
                && Objects.nonNull(branch.getFranchiseId());
    }

}
