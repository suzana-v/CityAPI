package com.svulinovic.cityapi.repository;

import com.svulinovic.cityapi.model.Favourite;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {

    Favourite getByUserIdAndCityId(Long userId, Long cityId);

    void deleteByUserIdAndCityId(Long userId, Long cityId);

}
