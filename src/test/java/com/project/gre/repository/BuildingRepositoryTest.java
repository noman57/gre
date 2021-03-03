package com.project.gre.repository;

import com.project.gre.model.Building;
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
public class BuildingRepositoryTest {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void createShouldCreate() {
        Building building = new Building();
        building.setName("b1");
        Building persistBuilding = testEntityManager.persistAndFlush(building);
        Optional<Building> buildingOptional = buildingRepository.findById(persistBuilding.getId());
        assertThat(buildingOptional).isPresent();
    }
}
