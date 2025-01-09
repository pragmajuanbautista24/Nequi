package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.providers.entity.Franchise;
import com.prueba.nequi.domain.usecase.FranchiseUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/franquicias", produces = MediaType.APPLICATION_JSON_VALUE)
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;

    public FranchiseController(FranchiseUseCase franchiseUseCase) {
        this.franchiseUseCase = franchiseUseCase;
    }

    @GetMapping
    public Flux<Franchise> getAll() {
        return franchiseUseCase.findAllFranchises();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<Franchise>> getById(@PathVariable Integer id) {
        return franchiseUseCase.findByIdFranchise(id);
    }

    @PostMapping
    public Mono<BasicResponse<Franchise>> create(@RequestBody Franchise franquicia) {
        return franchiseUseCase.saveFranchise(franquicia);
    }

    @PutMapping("/{id}")
    public Mono<BasicResponse<Franchise>> update(@PathVariable Integer id, @RequestBody Franchise franquicia) {
        return franchiseUseCase.updateFranchise(id, franquicia);
    }

    @DeleteMapping("/{id}")
    public Mono<BasicResponse<Franchise>> delete(@PathVariable Integer id) {
        return franchiseUseCase.deleteByIdFranchise(id);
    }

}
