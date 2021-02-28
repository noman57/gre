package com.project.gre.service;

import com.project.gre.model.Building;
import com.project.gre.model.dto.BuildingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuildingService {

    Page<Building> findAll(Pageable pageable);

    Building find(Long id);

    void delete(Long id);

    Building create(BuildingDTO buildingDTO) ;

    Building update(long buildingId, BuildingDTO BuildingDTO);
}
