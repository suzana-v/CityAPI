package com.svulinovic.cityapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCityRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long population;

}
