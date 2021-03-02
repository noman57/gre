package com.project.gre.controller;

import com.project.gre.model.Building;
import com.project.gre.dto.BuildingDTO;
import com.project.gre.service.BuildingService;
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
@RequestMapping("/v1/buildings")
@CrossOrigin("*")
public class BuildingController {

    private BuildingService buildingService;


    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public HttpEntity<Page<Building>> getBuildings(final Pageable pageable) {
        Page<Building> buildingPage = buildingService.findAll(pageable);
        return ResponseEntity.ok(buildingPage);
    }

    @GetMapping("/{buildingId}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long buildingId) {
        Building building = buildingService.find(buildingId);
        return ResponseEntity.ok(building);
    }

    @DeleteMapping("/{buildingId}")
    public void deleteBuilding(@PathVariable long buildingId) {
        buildingService.delete(buildingId);
    }

    @PostMapping
    public ResponseEntity<String> createBuilding(@Valid @RequestBody BuildingDTO buildingDTO,final UriComponentsBuilder ucBuilder)  {
        Building building = buildingService.create(buildingDTO);
        final URI uri = ucBuilder.path("/buildings/{id}").buildAndExpand(building.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{buildingId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateBuilding(@PathVariable long buildingId, @Valid @RequestBody BuildingDTO buildingDTO)  {
        buildingService.update(buildingId,buildingDTO);
        return ResponseEntity.ok().body("");
    }

}

