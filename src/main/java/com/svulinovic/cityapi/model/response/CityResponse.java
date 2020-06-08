package com.svulinovic.cityapi.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CityResponse {

    private Long id;

    private String name;

    private String description;

    private Long population;

    private LocalDateTime ctime;

}
