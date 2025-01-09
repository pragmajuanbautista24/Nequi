package com.prueba.nequi.controller;

import com.prueba.nequi.entity.Franchise;
import com.prueba.nequi.service.FranchiseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franquicias")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping
    public Flux<Franchise> getAll() {
        return franchiseService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Franchise> getById(@PathVariable Integer id) {
        return franchiseService.findById(id);
    }

    @PostMapping
    public Mono<Franchise> create(@RequestBody Franchise franquicia) {
        return franchiseService.save(franquicia);
    }

    @PutMapping("/{id}")
    public Mono<Franchise> update(@PathVariable Integer id, @RequestBody Franchise franquicia) {
        return franchiseService.findById(id)
                .flatMap(existing -> {
                    franquicia.setId(existing.getId());
                    return franchiseService.save(franquicia);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return franchiseService.deleteById(id);
    }

}
