package com.project.gre.model;

import com.project.gre.model.common.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Building extends EntityBase {

    @Column(nullable = false)
    private String name;

}
