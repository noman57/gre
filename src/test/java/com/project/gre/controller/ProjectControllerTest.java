package com.project.gre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gre.enamuration.ProjectStatus;
import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.filter.ProjectFilterDTO;
import com.project.gre.model.Building;
import com.project.gre.model.Person;
import com.project.gre.model.Project;
import com.project.gre.model.dto.ProjectDTO;
import com.project.gre.service.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;



    @Test
    public void findAllShouldReturnOK() throws Exception {
        when(projectService.findAll(any())).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/projects/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnProject() throws Exception {
        Project project = new Project();
        project.setId(1);
        project.setName("name");
        when(projectService.find(any())).thenReturn(project);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/projects/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldThrowException() throws Exception {
        when(projectService.find(any())).thenThrow(new ResourceNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/projects/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnCreated() throws Exception {
        Project project = new Project();
        project.setId(1);
        project.setName("name");
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("name");
        projectDTO.setPersonId(2l);
        projectDTO.setBuildingId(3l);
        projectDTO.setStatus(ProjectStatus.IN_PROGRESS);
        when(projectService.create(any())).thenReturn(project);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/projects/")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectDTO)
                ))
                .andExpect(status().isCreated());
    }


    @Test
    public void updateShouldReturnOK() throws Exception {
        Project project = new Project();
        project.setId(1);
        project.setName("name");
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("name");
        projectDTO.setPersonId(4l);
        projectDTO.setBuildingId(5l);
        projectDTO.setStatus(ProjectStatus.COMPLETE);
        when(projectService.update(anyLong(),any(ProjectDTO.class))).thenReturn(project);
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectDTO))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnThrowClientException() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO();
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/projects/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(projectDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnOk() throws Exception {
        doNothing().when(projectService).delete(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/projects/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByFilterShouldReturnProject() throws Exception {
        Project project = new Project();
        project.setId(1);
        project.setName("name");
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        Page<Project> pagedResponse = new PageImpl(projects);
        when(projectService.findByFilter(any(ProjectFilterDTO.class), any(PageRequest.class))).thenReturn(pagedResponse);
         mockMvc.perform(MockMvcRequestBuilders.get("/v1/projects/filter?personId=1&buildingId=1")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());;
    }
}
