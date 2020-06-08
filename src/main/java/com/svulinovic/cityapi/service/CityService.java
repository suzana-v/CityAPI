package com.svulinovic.cityapi.service;

import com.svulinovic.cityapi.exception.BadRequestException;
import com.svulinovic.cityapi.exception.ExceptionConstants;
import com.svulinovic.cityapi.model.City;
import com.svulinovic.cityapi.model.Favourite;
import com.svulinovic.cityapi.model.User;
import com.svulinovic.cityapi.model.mapper.Mapper;
import com.svulinovic.cityapi.model.request.CreateCityRequest;
import com.svulinovic.cityapi.model.response.CityResponse;
import com.svulinovic.cityapi.repository.CityRepository;
import com.svulinovic.cityapi.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Transactional
    public CityResponse create(CreateCityRequest request) {

        //check if city with provided name already exists
        City city = cityRepository.findByName(request.getName());
        if (city != null) {
            throw new BadRequestException(ExceptionConstants.CITY_EXISTS);
        }

        //save city to database
        city = cityRepository.save(Mapper.map(request));

        //create response object
        return Mapper.map(city);
    }

    @Transactional
    public void addToFavourites(User user, Long cityId) {

        Optional<City> optCity = cityRepository.findById(cityId);
        if (!optCity.isPresent()) {
            throw new BadRequestException(ExceptionConstants.CITY_NOT_FOUND);
        }

        //check if favourite already exists
        Favourite favourite = favouriteRepository.getByUserIdAndCityId(user.getId(), cityId);

        if (favourite == null) {
            //save favourite city for user
            favourite = new Favourite();
            favourite.setUser(user);
            favourite.setCity(optCity.get());
            favouriteRepository.save(favourite);
        }
    }

    @Transactional
    public void removeFromFavourites(User user, Long cityId) {

        Optional<City> optCity = cityRepository.findById(cityId);
        if (!optCity.isPresent()) {
            throw new BadRequestException(ExceptionConstants.CITY_NOT_FOUND);
        }

        //delete favourite city for user
        favouriteRepository.deleteByUserIdAndCityId(user.getId(), cityId);
    }

    public List<CityResponse> getAll() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    public List<CityResponse> sort(String sortBy) {
        List<City> cities;

        if ("favourite".equals(sortBy)) {
            cities = cityRepository.findAllSortByFavouriteCount();
        } else if ("ctime".equals(sortBy)) {
            cities = cityRepository.findAllByOrderByCtime();
        } else {
            throw new BadRequestException(ExceptionConstants.INVALID_SORTBY_VALUE);
        }

        return cities.stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

}
