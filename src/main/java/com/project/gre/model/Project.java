package com.project.gre.model;

import com.project.gre.model.common.EntityBase;
import lombok.Getter;
import lombok.Setter;
import com.project.gre.enamuration.ProjectStatus;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Project extends EntityBase {

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false)
    private ProjectStatus staus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
}
