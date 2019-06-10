package com.springframework.services;

import com.springframework.api.v2.model.UnitOfMeasureDTO;

import java.util.List;

public interface UnitOfMeasureService {
    List<UnitOfMeasureDTO> getListOfUoms();
}
