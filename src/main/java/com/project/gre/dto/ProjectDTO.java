package com.project.gre.dto;
import  com.project.gre.enamuration.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectDTO {

    @NotEmpty(message = "project name can not be empty")
    private String name;

    private String description;
    @NotNull
    private ProjectStatus status;

    @NotNull(message = "person id must not be null")
    private Long personId;

    @NotNull(message = "building id must not be null")
    private Long buildingId;
}
