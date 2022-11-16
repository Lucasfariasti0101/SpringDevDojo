package com.devdojo.academy.springboot.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePutRequestBody {
    @NotEmpty(message = "The anime id cannot be empty.")
    private Long id;
    @NotEmpty(message = "The anime name cannot be empty.")
    private String name;
}
