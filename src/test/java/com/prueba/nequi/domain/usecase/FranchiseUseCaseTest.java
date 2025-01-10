package com.prueba.nequi.domain.usecase;

import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.providers.adapter.FranchiseRepository;
import com.prueba.nequi.providers.entity.Franchise;
import com.prueba.nequi.providers.mapper.FranchiseMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FranchiseUseCaseTest {

    @InjectMocks
    private FranchiseUseCase useCase;

    @Mock
    private FranchiseRepository repository;

    @Mock
    private FranchiseMapper mapper;

    private FranchiseDto dto;

    @Before
    public void setUp() {
        dto = new FranchiseDto();
        dto.setId(1);
        dto.setName("Medellin");
        MockitoAnnotations.openMocks(this);
    }

    //@Test
    //public void findAllFranchisesTest() {
    //    Franchise franchise1 = new Franchise(1, "Franquicia A");
//
    //    FranchiseDto franchiseDto1 = new FranchiseDto(1, "Franquicia A");
//
    //    when(repository.findAll()).thenReturn(Flux.just(franchise1));
//
    //    when(mapper.toDto(franchise1)).thenReturn(franchiseDto1);
//
    //    Flux<FranchiseDto> result = useCase.findAllFranchises();
//
    //    StepVerifier.create(result)
    //            .expectNext(franchiseDto1)
    //            .verifyComplete();
    //}

}
