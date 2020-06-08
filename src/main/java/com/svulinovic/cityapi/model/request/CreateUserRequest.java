package com.svulinovic.cityapi.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank
    private String email; //napraviti validaciju formata emaila

    @NotBlank
    private String password;

}
