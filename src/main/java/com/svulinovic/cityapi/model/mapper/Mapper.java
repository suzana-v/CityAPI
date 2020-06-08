package com.svulinovic.cityapi.model.mapper;

import com.svulinovic.cityapi.model.City;
import com.svulinovic.cityapi.model.request.CreateCityRequest;
import com.svulinovic.cityapi.model.response.CityResponse;

public class Mapper {

    public static City map(CreateCityRequest in) {
        City city = new City();
        city.setName(in.getName());
        city.setDescription(in.getDescription());
        city.setPopulation(in.getPopulation());
        return city;
    }

    public static CityResponse map(City in) {
        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(in.getId());
        cityResponse.setName(in.getName());
        cityResponse.setDescription(in.getDescription());
        cityResponse.setPopulation(in.getPopulation());
        cityResponse.setCtime(in.getCtime());
        return cityResponse;
    }
}
