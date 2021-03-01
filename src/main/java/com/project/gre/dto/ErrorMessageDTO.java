package com.project.gre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ErrorMessageDTO {

    private HttpStatus status;
    private List errors;


    public ErrorMessageDTO(final HttpStatus status, final String message) {
        this.status = status;
        errors = Arrays.asList(message);
    }
}
