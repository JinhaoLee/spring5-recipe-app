package com.springframework.services;

import com.springframework.api.v2.mapper.UnitOfMeasureMapper;
import com.springframework.api.v2.model.UnitOfMeasureDTO;
import com.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private UnitOfMeasureMapper unitOfMeasureMapper;

    @Override
    public List<UnitOfMeasureDTO> getListOfUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureMapper::unitOfMeasureToUnitMeasureDTO)
                .collect(Collectors.toList());
    }
}
