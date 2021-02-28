package com.project.gre.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BuildingDTO {

    @NotEmpty(message = "Name can not be empty")
    private String name;
}
