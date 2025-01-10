package com.prueba.nequi.providers.mapper;

import com.prueba.nequi.domain.model.dto.ProductDto;
import com.prueba.nequi.providers.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product source);

    Product toEntity(ProductDto source);

}
