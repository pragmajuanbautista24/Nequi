package com.prueba.nequi.configuration;

import com.prueba.nequi.domain.usecase.BranchUseCase;
import com.prueba.nequi.domain.usecase.FranchiseUseCase;
import com.prueba.nequi.domain.usecase.ProductUseCase;
import com.prueba.nequi.providers.adapter.BranchRepository;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.adapter.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public FranchiseUseCase franchiseUseCase(FranchiseRepository repository) {
        return new FranchiseUseCase(repository);
    }

    @Bean
    public BranchUseCase branchUseCase(BranchRepository repository, FranchiseRepository franchiseRepository) {
        return new BranchUseCase(repository, franchiseRepository);
    }

    @Bean
    public ProductUseCase productUseCase(ProductRepository repository, BranchRepository branchRepository, DatabaseClient databaseClient) {
        return new ProductUseCase(repository, branchRepository, databaseClient);
    }

}
