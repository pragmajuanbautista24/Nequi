package com.prueba.nequi.entryendpoints;

import com.prueba.nequi.domain.model.response.BasicResponse;
import com.prueba.nequi.domain.usecase.BranchUseCase;
import com.prueba.nequi.providers.entity.Branch;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
    public Flux<Branch> getAll() {
        return branchUseCase.findAllBranchs();
    }

    @GetMapping("/{id}")
    public Mono<BasicResponse<Branch>> getById(@PathVariable Integer id) {
        return branchUseCase.findByIdBranch(id);
    }

    @PostMapping
    public Mono<BasicResponse<Branch>> create(@RequestBody Branch branch) {
        return branchUseCase.saveBranch(branch);
    }

    @PutMapping("/{id}")
    public Mono<BasicResponse<Branch>> update(@PathVariable Integer id, @RequestBody Branch branch) {
        return branchUseCase.updateBranch(id, branch);
    }

    @DeleteMapping("/{id}")
    public Mono<BasicResponse<Branch>> delete(@PathVariable Integer id) {
        return branchUseCase.deleteByIdBranch(id);
    }

}
