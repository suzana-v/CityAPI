package com.svulinovic.cityapi;

import com.svulinovic.cityapi.model.request.CreateCityRequest;
import com.svulinovic.cityapi.model.request.CreateUserRequest;
import com.svulinovic.cityapi.model.request.LoginRequest;
import com.svulinovic.cityapi.util.Helper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CityAPITests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createUser__login_200() throws Exception {

        CreateUserRequest request = getCreateUserRequest();

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Helper.asJsonString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        LoginRequest loginRequest = getLoginRequest();

        mvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Helper.asJsonString(loginRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createCity_401() throws Exception {

        CreateCityRequest request = getCreateCityRequest();

        mvc.perform(post("/cities")
                .header(AUTHORIZATION, getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Helper.asJsonString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addToFavourites_401() throws Exception {

        mvc.perform(post("/cities/1/favourites")
                .header(AUTHORIZATION, getToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void removeFromFavourites_401() throws Exception {

        mvc.perform(delete("/cities/1/favourites")
                .header(AUTHORIZATION, getToken()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllCities_200() throws Exception {

        mvc.perform(get("/cities")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCitiesSorted_200() throws Exception {

        mvc.perform(get("/cities/sorted")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private CreateUserRequest getCreateUserRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("suzana@gmail.com");
        request.setPassword("password");
        return request;
    }

    private LoginRequest getLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("suzana@gmail.com");
        request.setPassword("password");
        return request;
    }

    private String getToken() {
        return "e320dfdf-76cc-4b52-953f-717a25d12101";
    }

    private CreateCityRequest getCreateCityRequest() {
        CreateCityRequest request = new CreateCityRequest();
        request.setName("Brckovljani");
        request.setDescription("Selo pokraj Dugog Sela");
        request.setPopulation(2L);
        return request;
    }

}
