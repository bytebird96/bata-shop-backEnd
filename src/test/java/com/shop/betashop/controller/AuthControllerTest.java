package com.shop.betashop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.betashop.dto.SignupRequest;
import com.shop.betashop.dto.SignupResponse;
import com.shop.betashop.service.AuthService;
import com.shop.betashop.util.MessageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AuthController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        }
)
@MockBean(JpaMetamodelMappingContext.class)
class AuthControllerTest {
    @MockBean
    private AuthService authService;

    private SignupRequest signupRequest;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setEmail("test@email.com");
        signupRequest.setPassword("password");
        signupRequest.setUserName("test");
        signupRequest.setPhone("01012345678");
    }


    @Test
    @DisplayName("회원 조회 : 회원 아이디 존재 여부 확인 - 존재하는 경우")
    void shouldReturnTrueWhenUserExists() throws Exception {
        String userId = signupRequest.getEmail();
        given(authService.isUserExist(userId)).willReturn(true);

        mockMvc.perform(get("/api/auth/isUserExist")
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(""))
                .andExpect(jsonPath("$.result").value("Success"));

        verify(authService, times(1)).isUserExist(userId);
    }

    @Test
    @DisplayName("회원 조회 : 회원 아이디 존재 여부 확인 - 존재하지 않는 경우")
    void shouldReturnFalseWhenUserDoesNotExist() throws Exception {
        String userId = "unknownUser";

        given(authService.isUserExist(userId)).willReturn(Boolean.FALSE);

        mockMvc.perform(get("/api/auth/isUserExist")
                        .param("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));

        verify(authService, times(1)).isUserExist(userId);
    }

    @Test
    @DisplayName("회원 아이디 존재 여부 확인 - userId 파라미터 누락 시 예외 발생")
    void shouldReturnBadRequestWhenUserIdIsMissing() throws Exception {
        mockMvc.perform(get("/api/auth/isUserExist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());  // HTTP 400 상태 확인
    }


    @Test
    @DisplayName("회원 가입 이메일 중복")
    void shouldReturnBadRequestWhenUserEmailIsInvalid() throws Exception {
        SignupResponse signupResponse = new SignupResponse();
        signupResponse.setMessage(MessageProvider.DUPLICATE_EMAIL);

        given(authService.userRegister(signupRequest)).willReturn(signupResponse);

        mockMvc.perform(post("/api/auth/userRegister")
                        .content(objectMapper.writeValueAsString(signupRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MessageProvider.DUPLICATE_EMAIL));
    }
}