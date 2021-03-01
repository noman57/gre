package com.project.gre.service;

import com.project.gre.exception.ResourceNotFoundException;
import com.project.gre.model.Building;
import com.project.gre.dto.BuildingDTO;
import com.project.gre.repository.BuildingRepository;
import com.project.gre.service.impl.BuildingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuildingServiceTest {

    private BuildingService buildingService;

    @Mock
    private BuildingRepository buildingRepository;

    @Before
    public void setUp()
    {
        buildingService = new BuildingServiceImpl(buildingRepository);
    }



    @Test
    public void findAllShouldReturnAll() {
        Building buildingA = mock(Building.class);
        Building buildingB = mock(Building.class);

        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.ASC, "name");
        given(buildingRepository.findAll(pageRequest))
                .willReturn(new PageImpl<>(List.of(buildingA, buildingB), pageRequest, 2));

        Page<Building> results = buildingService.findAll(pageRequest);

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(buildingA);
        assertThat(results.getContent()).contains(buildingB);

    }

    @Test
    public void createBuildingShouldCreate() {
        Building buildingA = mock(Building.class);
        given(buildingRepository.save(any()))
                .willReturn(buildingA);
        Building building = buildingService.create(new BuildingDTO());
        assertThat(building).isNotNull();
        assertThat(building).isEqualTo(buildingA);

    }


    @Test
    public void updateShouldUpdate() {
        Building buildingA = new Building();
        buildingA.setName("name");
        buildingA.setId(1L);
        given(buildingRepository.findById(1L))
                .willReturn(Optional.of(buildingA));
        when(buildingRepository.save(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setName("newName");
        Building building = buildingService.update(1,buildingDTO);
        assertThat(building).isNotNull();
        assertThat(building.getName()).isEqualTo(buildingDTO.getName());

    }


    @Test
    public void findShouldReturnObject() {
        Building buildingA = mock(Building.class);
        given(buildingRepository.findById(1L))
                .willReturn(Optional.ofNullable(buildingA));
        Building building = buildingService.find(1L);
        assertThat(building).isNotNull();
        assertThat(building).isEqualTo(buildingA);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void findShouldThrowException() {
        given(buildingRepository.findById(1L))
                .willReturn(Optional.empty());
         buildingService.find(1L);

    }
    @Test
    public void deleteShouldDelete() {
        Building buildingA = mock(Building.class);
        given(buildingRepository.findById(1L))
                .willReturn(Optional.ofNullable(buildingA));
        doNothing().when(buildingRepository).delete(buildingA);
        buildingService.delete(1L);
        verify(buildingRepository).delete(buildingA);

    }
}
