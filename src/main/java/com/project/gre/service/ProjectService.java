package com.project.gre.service;
import com.project.gre.filter.ProjectFilterDTO;
import com.project.gre.model.Project;
import com.project.gre.model.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Page<Project> findAll(Pageable pageable);

    Project find(Long id);

    void delete(Long id);

    Project create(ProjectDTO projectDTO) ;

    Project update(long id, ProjectDTO projectDTO);

    Page<Project> findByFilter(ProjectFilterDTO propertyFilter, Pageable pageable);
}
