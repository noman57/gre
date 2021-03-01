package com.project.gre.service.impl;

import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Person;
import com.project.gre.model.dto.PersonDTO;
import com.project.gre.repository.PersonRepository;
import com.project.gre.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person find(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(()->new ResourceNotFoundException("No person found with id "+id));
    }

    @Override
    public void delete(Long id) {
        Person person = find(id);
        personRepository.delete(person);
    }

    @Override
    public Person create(PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        return personRepository.save(person);
    }

    @Override
    public Person update(long buildingId, PersonDTO personDTO) {
        Person person = find(buildingId);
        person.setName(personDTO.getName());
        return personRepository.save(person);
    }

}
