package com.project.gre.service;

import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.filter.ProjectFilterDTO;
import com.project.gre.filter.ProjectSpecification;
import com.project.gre.model.Building;
import com.project.gre.model.Person;
import com.project.gre.model.Project;
import com.project.gre.model.dto.PersonDTO;
import com.project.gre.model.dto.ProjectDTO;
import com.project.gre.repository.PersonRepository;
import com.project.gre.repository.ProjectRepository;
import com.project.gre.service.impl.PersonServiceImpl;
import com.project.gre.service.impl.ProjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private BuildingService buildingService;

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @Before
    public void setUp()
    {
        projectService = new ProjectServiceImpl(projectRepository,personService,buildingService);
    }



   @Test
    public void findAllShouldReturnAll() {
        Project projectA = mock(Project.class);
       Project projectB = mock(Project.class);

        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "name");
        given(projectRepository.findAll(pageRequest))
                .willReturn(new PageImpl<>(List.of(projectA, projectB), pageRequest, 2));

        Page<Project> results = projectService.findAll(pageRequest);

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(projectA);
        assertThat(results.getContent()).contains(projectB);

    }

    @Test
    public void createBuildingShouldCreate() {
        Person person = mock(Person.class);
        Building building = mock(Building.class);
        given(personService.find(2L))
                .willReturn(person);
        given(buildingService.find(3L))
                .willReturn(building);
        when(projectRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("newName");
        projectDTO.setDescription("newDes");
        projectDTO.setPersonId(2L);
        projectDTO.setBuildingId(3L);
        projectDTO.setStatus(projectDTO.getStatus());
        Project updatedProject = projectService.create(projectDTO);
        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getName()).isEqualTo(projectDTO.getName());
        assertThat(updatedProject.getBuilding()).isEqualTo(building);
        assertThat(updatedProject.getPerson()).isEqualTo(person);
        assertThat(updatedProject.getStaus()).isEqualTo(projectDTO.getStatus());

    }


    @Test
    public void updateShouldUpdate() {
        Person person = mock(Person.class);
        Building building = mock(Building.class);
        Project projectA = new Project();
        projectA.setName("name");
        projectA.setId(1L);
        given(projectRepository.findById(1L))
                .willReturn(Optional.of(projectA));
        given(personService.find(2L))
                .willReturn(person);
        given(buildingService.find(3L))
                .willReturn(building);
        when(projectRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("newName");
        projectDTO.setDescription("newDes");
        projectDTO.setPersonId(2L);
        projectDTO.setBuildingId(3L);
        projectDTO.setStatus(projectDTO.getStatus());
        Project updatedProject = projectService.update(1,projectDTO);
        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getName()).isEqualTo(projectDTO.getName());
        assertThat(updatedProject.getBuilding()).isEqualTo(building);
        assertThat(updatedProject.getPerson()).isEqualTo(person);
        assertThat(updatedProject.getStaus()).isEqualTo(projectDTO.getStatus());

    }


    @Test
    public void findShouldReturnObject() {
        Project project = mock(Project.class);
        given(projectRepository.findById(1L))
                .willReturn(Optional.ofNullable(project));
        Project returnedProject = projectService.find(1L);
        assertThat(returnedProject).isNotNull();
        assertThat(returnedProject).isEqualTo(project);

    }


    @Test(expected = ResourceNotFoundException.class)
    public void findShouldThrowException() {
        given(projectRepository.findById(1L))
                .willReturn(Optional.ofNullable(null));
         projectService.find(1L);

    }

    @Test
    public void deleteShouldDelete() {
        Project project = mock(Project.class);
        given(projectRepository.findById(1L))
                .willReturn(Optional.ofNullable(project));
        doNothing().when(projectRepository).delete(project);
        projectService.delete(1L);
        verify(projectRepository).delete(project);
    }

    @Test
    public void findProjectsByFilterAndReturnSuccess() {
        doReturn(Page.empty()).when(projectRepository).findAll(any(ProjectSpecification.class), any(Pageable.class));
        projectService.findByFilter(mock(ProjectFilterDTO.class), Pageable.unpaged());
    }
}
