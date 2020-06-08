package com.svulinovic.cityapi;

import com.svulinovic.cityapi.controller.UserController;
import com.svulinovic.cityapi.model.request.CreateUserRequest;
import com.svulinovic.cityapi.model.request.LoginRequest;
import com.svulinovic.cityapi.service.UserService;
import com.svulinovic.cityapi.util.Helper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUser_200() throws Exception {

        CreateUserRequest request = getCreateUserRequest();

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Helper.asJsonString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void login_200() throws Exception {

        LoginRequest request = getLoginRequest();

        mvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Helper.asJsonString(request))
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
}
