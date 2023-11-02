package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.application.SignupCreateApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignupController.class)
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private SignupCreateApplication signupCreateApplication;

    private String body;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        body = mapper.writeValueAsString(new SignupController.SignupRequest("gusals@naver.com"));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이미 생성한 계정이 존재하면 가입 요청은 실패한다.")
    void signupException() throws Exception {
        doThrow(new IllegalArgumentException()).when(signupCreateApplication).signup(any());

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
        verify(signupCreateApplication).signup(any());
    }

    @Test
    @WithMockUser
    @DisplayName("성공: 가입 요청을 성공한다.")
    void signup() throws Exception {
        doNothing().when(signupCreateApplication).signup(any());

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
        verify(signupCreateApplication).signup(any());
    }


}