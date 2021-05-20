package com.jupiter.failfast.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jupiter.failfast.user.api.dto.UserDto;
import com.jupiter.failfast.user.entity.User;
import com.jupiter.failfast.user.exception.EmptyEmailException;
import com.jupiter.failfast.user.exception.UserExistException;
import com.jupiter.failfast.user.mapper.UserMapper;
import com.jupiter.failfast.user.repository.UserRepository;
import com.jupiter.failfast.user.service.UserService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*@SpringBootTest
@AutoConfigureMockMvc*/
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void create_SendValidParams_shouldReturnEmail() throws Exception {
        //GIVEN:
        String expectedEmail = "test";
        UserDto userDto = getUserDto(expectedEmail);
        when(userService.create(any())).thenReturn(expectedEmail);
        when(userMapper.map(userDto)).thenReturn(getUser(userDto));

        //WHEN:
        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                //@formatter:off
        //THEN:
                //@formatter:on
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedEmail)));
    }

    @Test
    void create_SendUserWithEmptyEmail_ShouldThrowEmptyEmailException() throws Exception {
        //GIVEN:
        String expectedErrorMessage = "error message";
        UserDto userDto = getUserDto();
        doThrow(new EmptyEmailException(expectedErrorMessage)).when(userService).create(any());
        when(userMapper.map(any())).thenReturn(getUser(userDto));

        //WHEN:
        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                //@formatter:off
        //THEN:
                //@formatter:on
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(expectedErrorMessage)));
    }

    @Test
    void create_SendExistUser_ShouldThrowUserExistException() throws Exception {
        //GIVEN:
        String expectedErrorMessage = "error message";
        UserDto userDto = getUserDto();
        doThrow(new UserExistException(expectedErrorMessage)).when(userService).create(any());
        when(userMapper.map(any())).thenReturn(getUser(userDto));

        //WHEN:
        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                //@formatter:off
        //THEN:
                //@formatter:on
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(expectedErrorMessage)));
    }

    /*@Test
    void create_SendExistUser_ShouldThrowValidateException() throws Exception {
        //GIVEN
        String expectedMessage = "User already exist";
        UserDto userDto = getUserDto();
        when(repository.getUserByEmail(userDto.getEmail())).thenReturn(getUser());

        //WHEN:
        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                //@formatter:off
        //THEN:
                //@formatter:on
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].message").value(expectedMessage));
    }

    @Test
    void create_SendEmptyEmail_ShouldThrowValidateException() throws Exception {
        //GIVEN
        String expectedMessage = "must not be blank";
        UserDto userDto = getUserDto("");
        when(repository.getUserByEmail(userDto.getEmail())).thenReturn(null);

        //WHEN:
        // Execute the POST request
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                //@formatter:off
        //THEN:
                //@formatter:on
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].message").value(expectedMessage));
    }*/

    private User getUser() {
        return new User("", "", "");
    }

    private User getUser(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getFirstName(), userDto.getSecondName());
    }

    private UserDto getUserDto() {
        return getUserDto("email");
    }

    private UserDto getUserDto(String expectedEmail) {
        return new UserDto()
                .setEmail(expectedEmail)
                .setFirstName("firstName")
                .setSecondName("secondName");
    }

    String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}