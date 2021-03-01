package com.project.gre.controller;

import com.project.gre.model.Person;
import com.project.gre.model.dto.PersonDTO;
import com.project.gre.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/persons")
@CrossOrigin
public class PersonController {

    private PersonService personService;


    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public HttpEntity<Page<Person>> getPersons(final Pageable pageable) {
        Page<Person> buildingPage = personService.findAll(pageable);
        return ResponseEntity.ok(buildingPage);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> gerPersonById(@PathVariable Long personId) {
        Person person = personService.find(personId);
        return ResponseEntity.ok(person);
    }

    @DeleteMapping("/{personId}")
    public void deletePerson(@PathVariable long personId) {
        personService.delete(personId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonDTO personDTO, final UriComponentsBuilder ucBuilder)  {
        Person person = personService.create(personDTO);
        final URI uri = ucBuilder.path("/persons/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updatePerson(@PathVariable long personId, @Valid @RequestBody PersonDTO personDTO)  {
        personService.update(personId,personDTO);
        return ResponseEntity.ok().body("");
    }

}

