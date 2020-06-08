package com.svulinovic.cityapi.repository;

import com.svulinovic.cityapi.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);

    List<City> findAllByOrderByCtime();

    @Query(nativeQuery = true,
            value = "select c.* from City c left join Favourite f on c.id = f.cityId group by c.id order by sum(f.userId) desc")
    List<City> findAllSortByFavouriteCount();

}
