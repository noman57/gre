package com.project.gre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Building;
import com.project.gre.dto.BuildingDTO;
import com.project.gre.service.BuildingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BuildingController.class)
public class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;


    @Test
    public void findAllShouldReturnSuccess() throws Exception {
        when(buildingService.findAll(any())).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/buildings/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnBuilding() throws Exception {
        Building buildingA = new Building();
        buildingA.setId(1);
        buildingA.setName("name");
        when(buildingService.find(any())).thenReturn(buildingA);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/buildings/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldThrowException() throws Exception {
        when(buildingService.find(any())).thenThrow(new ResourceNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/buildings/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnCreated() throws Exception {
        Building buildingA = new Building();
        buildingA.setId(1);
        buildingA.setName("name");
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName("name");

        when(buildingService.create(any())).thenReturn(buildingA);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/buildings/")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(buildingDTO)
                ))
                .andExpect(status().isCreated());
    }


    @Test
    public void updateShouldReturnOK() throws Exception {
        Building buildingA = new Building();
        buildingA.setId(1);
        buildingA.setName("name");
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName("name");

        when(buildingService.update(anyLong(),any(BuildingDTO.class))).thenReturn(buildingA);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/buildings/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(buildingDTO))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnThrowClientException() throws Exception {
        BuildingDTO buildingDTO = new BuildingDTO();
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/buildings/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(buildingDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnOk() throws Exception {
        doNothing().when(buildingService).delete(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/buildings/1"))
                .andExpect(status().isOk());
    }
}
