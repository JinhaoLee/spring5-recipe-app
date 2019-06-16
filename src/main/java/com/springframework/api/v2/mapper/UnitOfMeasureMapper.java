package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.UnitOfMeasureDTO;
import com.springframework.models.UnitOfMeasure;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface UnitOfMeasureMapper {
    UnitOfMeasureDTO unitOfMeasureToUnitMeasureDTO(UnitOfMeasure unitOfMeasure);
}
