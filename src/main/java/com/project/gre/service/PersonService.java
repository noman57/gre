package com.project.gre.service;

import com.project.gre.model.Building;
import com.project.gre.model.Person;
import com.project.gre.model.dto.BuildingDTO;
import com.project.gre.model.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

    Page<Person> findAll(Pageable pageable);

    Person find(Long id);

    void delete(Long id);

    Person create(PersonDTO personDTO) ;

    Person update(long id, PersonDTO personDTO);
}
