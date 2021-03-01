package com.project.gre.controller;

import com.project.gre.model.Person;
import com.project.gre.model.Project;
import com.project.gre.model.dto.PersonDTO;
import com.project.gre.model.dto.ProjectDTO;
import com.project.gre.service.ProjectService;
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
@RequestMapping("/v1/projects")
@CrossOrigin
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public HttpEntity<Page<Project>> getProjects(final Pageable pageable) {
        Page<Project> projectPage = projectService.findAll(pageable);
        return ResponseEntity.ok(projectPage);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Project project = projectService.find(projectId);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable long projectId) {
        projectService.delete(projectId);
    }

    @PostMapping()
    public ResponseEntity<String> createProject(@Valid @RequestBody ProjectDTO projectDTO, final UriComponentsBuilder ucBuilder)  {
        Project person = projectService.create(projectDTO);
        final URI uri = ucBuilder.path("/projects/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<String> updateProject(@PathVariable long projectId,@Valid @RequestBody ProjectDTO projectDTO)  {
        projectService.update(projectId,projectDTO);
        return ResponseEntity.ok().body("");
    }

}

