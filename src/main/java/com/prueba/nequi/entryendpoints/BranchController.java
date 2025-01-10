package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.dto.BranchDto;
import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.BranchUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/sucursales", produces = MediaType.APPLICATION_JSON_VALUE)
public class BranchController {

    private final BranchUseCase branchUseCase;

    public BranchController(BranchUseCase branchUseCase) {
        this.branchUseCase = branchUseCase;
    }

    @GetMapping
    public Flux<BranchDto> getAll() {
        return branchUseCase.findAllBranchs();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<BranchDto>> getById(@PathVariable Integer id) {
        return branchUseCase.findByIdBranch(id);
    }

}
