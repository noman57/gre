package com.project.gre.initializer;

import com.project.gre.dto.BuildingDTO;
import com.project.gre.dto.PersonDTO;
import com.project.gre.dto.ProjectDTO;
import com.project.gre.enamuration.ProjectStatus;
import com.project.gre.model.Building;
import com.project.gre.model.Person;
import com.project.gre.service.BuildingService;
import com.project.gre.service.PersonService;
import com.project.gre.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
// this class should be ignored for any real world senario this just creates test data
@Component
@Slf4j
public class DataInitializer {


    private BuildingService buildingService;


    private PersonService personService;

    private ProjectService projectService;

    public DataInitializer(BuildingService buildingService, PersonService personService, ProjectService projectService) {
        this.buildingService = buildingService;
        this.personService = personService;
        this.projectService = projectService;
    }

    @PostConstruct
    public void init() {
        for(int i=0;i<5;i++){
            Building building = createSampleBuilding(i);
            Person person = createSamplePerson(i);
            createSampleProject(i, building, person);
        }
    }

    private Person createSamplePerson(int i) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("person "+i);
        return personService.create(personDTO);
    }

    private void createSampleProject(int i, Building building, Person person) {
        ProjectDTO project = new ProjectDTO();
        project.setName("task "+i);
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setBuildingId(building.getId());
        project.setPersonId(person.getId());
        projectService.create(project);
    }

    private Building createSampleBuilding(int i) {
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName("building "+i);
       return buildingService.create(buildingDTO);
    }
}
