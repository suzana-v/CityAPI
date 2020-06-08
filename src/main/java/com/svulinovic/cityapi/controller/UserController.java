package com.svulinovic.cityapi.controller;

import com.svulinovic.cityapi.exception.ExceptionConstants;
import com.svulinovic.cityapi.exception.ExceptionInfo;
import com.svulinovic.cityapi.model.request.CreateUserRequest;
import com.svulinovic.cityapi.model.request.LoginRequest;
import com.svulinovic.cityapi.model.response.LoginResponse;
import com.svulinovic.cityapi.service.UserService;
import com.svulinovic.cityapi.util.Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "User")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.MISSING_REQUIRED_PARAMETER + " / " + ExceptionConstants.USER_EXISTS, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @PostMapping
    public LoginResponse create(@Valid @RequestBody CreateUserRequest request, BindingResult result) {

        Helper.validateRequest(result);
        return userService.create(request);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = ExceptionConstants.MISSING_REQUIRED_PARAMETER + " / " + ExceptionConstants.USER_NOT_FOUND, response = ExceptionInfo.class),
            @ApiResponse(code = 401, message = ExceptionConstants.ACCESS_DENIED, response = ExceptionInfo.class),
            @ApiResponse(code = 500, message = ExceptionConstants.INTERNAL_SERVER_ERROR, response = ExceptionInfo.class)})
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request, BindingResult result) {

        Helper.validateRequest(result);
        return userService.login(request);
    }
}
