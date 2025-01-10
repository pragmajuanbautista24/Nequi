package com.prueba.nequi.providers.mapper;

import com.prueba.nequi.domain.model.dto.BranchDto;
import com.prueba.nequi.providers.entity.Branch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    BranchDto toDto(Branch source);

    Branch toEntity(BranchDto source);

}
