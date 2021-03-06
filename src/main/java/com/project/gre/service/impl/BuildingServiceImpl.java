package com.project.gre.service.impl;

import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Building;
import com.project.gre.dto.BuildingDTO;
import com.project.gre.repository.BuildingRepository;
import com.project.gre.repository.ProjectRepository;
import com.project.gre.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    private BuildingRepository buildingRepository;

    private ProjectRepository projectRepository;

    @Autowired
    public BuildingServiceImpl(BuildingRepository buildingRepository, ProjectRepository projectRepository) {
        this.buildingRepository = buildingRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Page<Building> findAll(Pageable pageable) {
        return buildingRepository.findAll(pageable);
    }

    @Override
    public Building find(Long id) {
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        return buildingOptional.orElseThrow(() -> new ResourceNotFoundException("No building found with id " + id));
    }

    @Override
    public void delete(Long id) {
        Building building = find(id);
        if (projectRepository.countByBuilding_Id(id) == 0) {
            buildingRepository.delete(building);
        }else{
            throw new IllegalArgumentException("Building already linked with project");
        }

    }

    @Override
    public Building create(BuildingDTO buildingDTO) {
        Building building = new Building();
        building.setName(buildingDTO.getName());
        return buildingRepository.save(building);
    }

    @Override
    public Building update(long buildingId, BuildingDTO buildingDTO) {
        Building building = find(buildingId);
        building.setName(buildingDTO.getName());
        return buildingRepository.save(building);
    }
}
