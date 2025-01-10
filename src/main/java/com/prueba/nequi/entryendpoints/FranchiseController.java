package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.FranchiseUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Flux<FranchiseDto> getAll() {
        return franchiseUseCase.findAllFranchises();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<FranchiseDto>> getById(@PathVariable Integer id) {
        return franchiseUseCase.findByIdFranchise(id);
    }

}
