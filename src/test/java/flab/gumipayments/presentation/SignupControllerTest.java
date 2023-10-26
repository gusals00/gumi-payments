package flab.gumipayments.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.application.SignupCreateApplication;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignupController.class)
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private SignupCreateApplication signupCreateApplication;

    @Test
    @WithMockUser
    @DisplayName("가입 요청 API[POST] 테스트")
    void signup() throws Exception {
        String email = "love470@naver.com";
        doNothing().when(signupCreateApplication).signup(any());

        String body = mapper.writeValueAsString(new SignupController.SignupRequest(email));
        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists());
    }
}