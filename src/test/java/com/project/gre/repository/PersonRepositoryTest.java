package com.project.gre.repository;

import com.project.gre.model.Building;
import com.project.gre.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

    @Autowired
    private  PersonRepository personRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void createShouldCreate()
    {
        Person person = new Person();
        person.setName("b1");
        Person presistPerson = testEntityManager.persistAndFlush(person);
        Optional<Person> buildingOptional = personRepository.findById(presistPerson.getId());
        assertThat(buildingOptional.isPresent()).isTrue();
    }

}
