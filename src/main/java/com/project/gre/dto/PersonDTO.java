package com.project.gre.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PersonDTO {
    @NotEmpty(message = "Name can not be empty")
    private String name;
}
