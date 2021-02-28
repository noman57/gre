package com.project.gre.filter;

import com.project.gre.model.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification implements Specification<Project> {

    private ProjectFilterDTO criteria;

    public ProjectSpecification(ProjectFilterDTO criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getBuildingId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("building").get("id"), criteria.getBuildingId()));
        }
        if (criteria.getPersonId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("person").get("id"), criteria.getPersonId()));
        }
        return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).getRestriction();
    }
}
