package com.project.gre.repository;

import com.project.gre.filter.ProjectFilterDTO;
import com.project.gre.filter.ProjectSpecification;
import com.project.gre.model.Building;
import com.project.gre.model.Person;
import com.project.gre.model.Project;
import com.project.gre.enamuration.ProjectStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void createShouldCreateNewProject() {
        Project project = new Project();
        project.setName("b1");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        Project persistBuilding = testEntityManager.persistAndFlush(project);
        Optional<Project> projectOptional = projectRepository.findById(persistBuilding.getId());
        assertThat(projectOptional).isPresent();
    }


    @Test
    public void filterProjectShouldReturnForNull() {
        Building building = new Building();
        building.setName("name");
        testEntityManager.persistAndFlush(building);
        Person person = new Person();
        person.setName("name");
        person = testEntityManager.persistAndFlush(person);
        Project project = new Project();
        project.setName("b1");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setBuilding(building);
        project.setPerson(person);
        testEntityManager.persistAndFlush(project);

        ProjectSpecification projectSpecification = new ProjectSpecification(new ProjectFilterDTO());

        Page<Project> projectPage = projectRepository.findAll(projectSpecification,Pageable.unpaged());
        assertThat(projectPage).hasSize(1);
    }

    @Test
    public void filterProjectForRightValue() {
        Building building = new Building();
        building.setName("name");
        testEntityManager.persistAndFlush(building);
        Person person = new Person();
        person.setName("name");
        person = testEntityManager.persistAndFlush(person);
        Project project = new Project();
        project.setName("b1");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setBuilding(building);
        project.setPerson(person);
        testEntityManager.persistAndFlush(project);
        ProjectFilterDTO criteria = new ProjectFilterDTO();
        criteria.setBuildingId(building.getId());
        criteria.setPersonId(person.getId());

        ProjectSpecification projectSpecification = new ProjectSpecification(criteria);

        Page<Project> projectPage = projectRepository.findAll(projectSpecification,Pageable.unpaged());
        assertThat(projectPage).hasSize(1);
    }

    @Test
    public void filterProjectShouldReturnNone() {
        Building building = new Building();
        building.setName("name");
        testEntityManager.persistAndFlush(building);
        Person person = new Person();
        person.setName("name");
        person = testEntityManager.persistAndFlush(person);
        Project project = new Project();
        project.setName("b1");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setBuilding(building);
        project.setPerson(person);
        testEntityManager.persistAndFlush(project);
        ProjectFilterDTO criteria = new ProjectFilterDTO();
        criteria.setBuildingId(55L);
        criteria.setPersonId(66L);

        ProjectSpecification projectSpecification = new ProjectSpecification(criteria);

        Page<Project> projectPage = projectRepository.findAll(projectSpecification,Pageable.unpaged());
        assertThat(projectPage).isEmpty();
    }



}
