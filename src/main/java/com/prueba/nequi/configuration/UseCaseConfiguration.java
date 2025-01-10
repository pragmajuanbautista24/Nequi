package com.prueba.nequi.configuration;

import com.prueba.nequi.domain.usecase.BranchUseCase;
import com.prueba.nequi.domain.usecase.FranchiseUseCase;
import com.prueba.nequi.domain.usecase.ProductUseCase;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.adapter.ProductRepository;
import com.prueba.nequi.providers.mapper.BranchMapper;
import com.prueba.nequi.providers.mapper.FranchiseMapper;
import com.prueba.nequi.providers.mapper.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public FranchiseUseCase franchiseUseCase(FranchiseRepository repository, FranchiseMapper mapper) {
        return new FranchiseUseCase(repository, mapper);
    }

    @Bean
    public BranchUseCase branchUseCase(BranchRepository repository, FranchiseRepository franchiseRepository, BranchMapper mapper) {
        return new BranchUseCase(repository, franchiseRepository, mapper);
    }

    @Bean
    public ProductUseCase productUseCase(ProductRepository repository, BranchRepository branchRepository, ProductMapper mapper) {
        return new ProductUseCase(repository, branchRepository, mapper);
    }

}
