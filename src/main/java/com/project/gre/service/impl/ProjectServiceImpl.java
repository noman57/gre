package com.project.gre.service.impl;

import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.filter.ProjectFilterDTO;
import com.project.gre.filter.ProjectSpecification;
import com.project.gre.model.Project;
import com.project.gre.model.dto.ProjectDTO;
import com.project.gre.repository.ProjectRepository;
import com.project.gre.service.BuildingService;
import com.project.gre.service.PersonService;
import com.project.gre.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private PersonService personService;

    private BuildingService buildingService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, PersonService personService, BuildingService buildingService) {
        this.projectRepository = projectRepository;
        this.personService = personService;
        this.buildingService = buildingService;
    }


    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project find(Long id) {
        Optional<Project> person = projectRepository.findById(id);
        return person.orElseThrow(()->new ResourceNotFoundException("No person found with id "+id));
    }

    @Override
    public void delete(Long id) {
        Project project = find(id);
        projectRepository.delete(project);;
    }

    @Override
    public Project create(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStaus(projectDTO.getStatus());
        project.setBuilding(buildingService.find(projectDTO.getBuildingId()));
        project.setPerson(personService.find(projectDTO.getPersonId()));
        return projectRepository.save(project);
    }

    @Override
    public Project update(long projectId, ProjectDTO projectDTO) {
        Project project = find(projectId);
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStaus(projectDTO.getStatus());
        project.setBuilding(buildingService.find(projectDTO.getBuildingId()));
        project.setPerson(personService.find(projectDTO.getPersonId()));
        return projectRepository.save(project);
    }

    @Override
    public Page<Project> findByFilter(ProjectFilterDTO propertyFilter, Pageable pageable) {
        ProjectSpecification projectSpecification = new ProjectSpecification(propertyFilter);
        return projectRepository.findAll(projectSpecification, pageable);
    }
}
