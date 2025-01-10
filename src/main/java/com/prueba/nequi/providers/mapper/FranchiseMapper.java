package com.prueba.nequi.providers.mapper;

import com.prueba.nequi.domain.model.dto.FranchiseDto;
import com.prueba.nequi.providers.entity.Franchise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    FranchiseDto toDto(Franchise source);

    Franchise toEntity(FranchiseDto source);

}
