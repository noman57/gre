package com.project.gre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Person;
import com.project.gre.dto.PersonDTO;
import com.project.gre.service.PersonService;
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
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;



    @Test
    public void findAllShouldReturnOK() throws Exception {
        when(personService.findAll(any())).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/persons/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnPerson() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("name");
        when(personService.find(any())).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/persons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldThrowException() throws Exception {
        when(personService.find(any())).thenThrow(new ResourceNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnCreated() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("name");
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("name");
        when(personService.create(any())).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/persons/")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(personDTO)
                ))
                .andExpect(status().isCreated());
    }


    @Test
    public void updateShouldReturnOK() throws Exception {
        Person person = new Person();
        person.setId(1);
        person.setName("name");
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("name");
        when(personService.update(anyLong(),any(PersonDTO.class))).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(personDTO))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnThrowClientException() throws Exception {
        PersonDTO personDTO = new PersonDTO();
        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(personDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnOk() throws Exception {
        doNothing().when(personService).delete(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/persons/1"))
                .andExpect(status().isOk());
    }
}
