package com.svulinovic.cityapi.controller;

import com.svulinovic.cityapi.exception.ExceptionConstants;
import com.svulinovic.cityapi.exception.ExceptionInfo;
import com.svulinovic.cityapi.model.User;
import com.svulinovic.cityapi.model.request.CreateCityRequest;
import com.svulinovic.cityapi.model.response.CityResponse;
import com.svulinovic.cityapi.service.CityService;
import com.svulinovic.cityapi.service.UserService;
import com.svulinovic.cityapi.util.Helper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Api(tags = "City")
@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.MISSING_REQUIRED_PARAMETER + " / " + ExceptionConstants.CITY_EXISTS, response = ExceptionInfo.class),
            @ApiResponse(code = 401, message = ExceptionConstants.ACCESS_DENIED, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @PostMapping
    public CityResponse create(@ApiParam(required = true, value = "token") @NotBlank @RequestHeader(name = AUTHORIZATION) String token,
                               @Valid @RequestBody CreateCityRequest request, BindingResult result) {

        userService.findByToken(token);
        Helper.validateRequest(result);
        return cityService.create(request);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.CITY_NOT_FOUND, response = ExceptionInfo.class),
            @ApiResponse(code = 401, message = ExceptionConstants.ACCESS_DENIED, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @PostMapping("/{cityId}/favourites")
    public void addToFavourites(@ApiParam(required = true, value = "token") @NotBlank @RequestHeader(name = AUTHORIZATION) String token,
                                @PathVariable long cityId) {

        User user = userService.findByToken(token);
        cityService.addToFavourites(user, cityId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.CITY_NOT_FOUND, response = ExceptionInfo.class),
            @ApiResponse(code = 401, message = ExceptionConstants.ACCESS_DENIED, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @DeleteMapping("/{cityId}/favourites")
    public void removeFromFavourites(@ApiParam(required = true, value = "token") @NotBlank @RequestHeader(name = AUTHORIZATION) String token,
                                     @PathVariable long cityId) {

        User user = userService.findByToken(token);
        cityService.removeFromFavourites(user, cityId);
    }

    @ApiResponses(value = {@ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @GetMapping
    public List<CityResponse> getAll() {

        return cityService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.INVALID_SORTBY_VALUE, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @GetMapping("/sorted")
    public List<CityResponse> sort(@ApiParam(allowableValues = "ctime, favourite") @RequestParam(required = false, defaultValue = "ctime") String sortBy) {

        return cityService.sort(sortBy);
    }
}
