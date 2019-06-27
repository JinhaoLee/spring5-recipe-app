package com.springframework.api.v2.mapper;

import com.springframework.api.v2.model.UnitOfMeasureDTO;
import com.springframework.models.UnitOfMeasure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureMapper {
  UnitOfMeasureDTO unitOfMeasureToUnitMeasureDTO(UnitOfMeasure unitOfMeasure);
}
