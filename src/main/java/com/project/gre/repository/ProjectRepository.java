package com.project.gre.repository;

import com.project.gre.model.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project,Long> , JpaSpecificationExecutor<Project> {

    int countByBuilding_Id(long id);

    int countByPerson_Id(long id);
}
