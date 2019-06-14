package com.chakans.account.web.rest;

import com.chakans.account.domain.User;
import com.chakans.account.repository.UserRepository;
import com.chakans.account.web.rest.anonymous.UserJWTController;
import com.chakans.account.web.rest.anonymous.model.request.SignInRequestModel;
import com.chakans.core.security.jwt.TokenProvider;
import com.chakans.core.web.rest.errors.ExceptionTranslator;
import com.chakans.portal.ChakansApp;
import com.chakans.portal.web.rest.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

/**
 * Integration tests for the {@link UserJWTController} REST controller.
 */
@SpringBootTest(classes = ChakansApp.class)
public class UserJWTControllerIT {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        UserJWTController userJWTController = new UserJWTController(tokenProvider, authenticationManager);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userJWTController)
            .setControllerAdvice(exceptionTranslator)
            .build();
    }

    @Test
    @Transactional
    public void testAuthorize() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-controller");
        user.setEmail("user-jwt-controller@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        userRepository.saveAndFlush(user);

        SignInRequestModel signInRequestModel = new SignInRequestModel();
        signInRequestModel.setUsername("user-jwt-controller");
        signInRequestModel.setPassword("test");
        mockMvc.perform(post("/apis/authenticate")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signInRequestModel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(isEmptyString())));
    }

    @Test
    @Transactional
    public void testAuthorizeWithRememberMe() throws Exception {
        User user = new User();
        user.setLogin("user-jwt-controller-remember-me");
        user.setEmail("user-jwt-controller-remember-me@example.com");
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("test"));

        userRepository.saveAndFlush(user);

        SignInRequestModel signInRequestModel = new SignInRequestModel();
        signInRequestModel.setUsername("user-jwt-controller-remember-me");
        signInRequestModel.setPassword("test");
        signInRequestModel.setRememberMe(true);
        mockMvc.perform(post("/apis/authenticate")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signInRequestModel)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(isEmptyString())));
    }

    @Test
    public void testAuthorizeFails() throws Exception {
        SignInRequestModel signInRequestModel = new SignInRequestModel();
        signInRequestModel.setUsername("wrong-user");
        signInRequestModel.setPassword("wrong password");
        mockMvc.perform(post("/apis/authenticate")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signInRequestModel)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.id_token").doesNotExist())
            .andExpect(header().doesNotExist("Authorization"));
    }
}
