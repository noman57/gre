package com.project.gre.service;

import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Person;
import com.project.gre.dto.PersonDTO;
import com.project.gre.repository.PersonRepository;
import com.project.gre.service.impl.PersonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Before
    public void setUp()
    {
        personService = new PersonServiceImpl(personRepository);
    }



    @Test
    public void findAllShouldReturnAll() {
        Person personA = mock(Person.class);
        Person personB = mock(Person.class);

        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "name");
        given(personRepository.findAll(pageRequest))
                .willReturn(new PageImpl<>(List.of(personA, personB), pageRequest, 2));

        Page<Person> results = personService.findAll(pageRequest);

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(personA);
        assertThat(results.getContent()).contains(personB);

    }

    @Test
    public void createBuildingShouldCreate() {
        Person personA = mock(Person.class);
        given(personRepository.save(any()))
                .willReturn(personA);
        Person person = personService.create(new PersonDTO());
        assertThat(person).isNotNull();
        assertThat(person).isEqualTo(personA);

    }


    @Test
    public void updateShouldUpdate() {
        Person person = new Person();
        person.setName("name");
        person.setId(1L);
        given(personRepository.findById(1L))
                .willReturn(Optional.of(person));
        when(personRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("newName");
        Person updatedPerson = personService.update(1,personDTO);
        assertThat(updatedPerson).isNotNull();
        assertThat(updatedPerson.getName()).isEqualTo(personDTO.getName());

    }


    @Test
    public void findShouldReturnObject() {
        Person personA = mock(Person.class);
        given(personRepository.findById(1L))
                .willReturn(Optional.ofNullable(personA));
        Person person = personService.find(1L);
        assertThat(person).isNotNull();
        assertThat(person).isEqualTo(personA);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void findShouldThrowException() {
        given(personRepository.findById(1L))
                .willReturn(Optional.empty());
         personService.find(1L);

    }
    @Test
    public void deleteShouldDelete() {
        Person personA = mock(Person.class);
        given(personRepository.findById(1L))
                .willReturn(Optional.ofNullable(personA));
        doNothing().when(personRepository).delete(personA);
        personService.delete(1L);
        verify(personRepository).delete(personA);

    }
}
