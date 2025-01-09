package com.prueba.nequi.service;

import com.prueba.nequi.entity.Franchise;
import com.prueba.nequi.repository.FranchiseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;

    public FranchiseService(FranchiseRepository franquiciaRepository) {
        this.franchiseRepository = franquiciaRepository;
    }

    public Flux<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    public Mono<Franchise> findById(Integer id) {
        return franchiseRepository.findById(id);
    }

    public Mono<Franchise> save(Franchise franquicia) {
        return franchiseRepository.save(franquicia);
    }

    public Mono<Void> deleteById(Integer id) {
        return franchiseRepository.deleteById(id);
    }

}
