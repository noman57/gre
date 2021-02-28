package com.project.gre.repository;

import com.project.gre.model.Building;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository  extends PagingAndSortingRepository<Building ,Long> {
}
