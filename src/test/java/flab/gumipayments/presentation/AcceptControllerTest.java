package flab.gumipayments.presentation;

import flab.gumipayments.application.SignupAcceptApplication;
import flab.gumipayments.domain.KeyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AcceptController.class)
class AcceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SignupAcceptApplication signupAcceptApplication;

    private MultiValueMap<String, String> request;

    @BeforeEach
    void setUp() {
        request = new LinkedMultiValueMap<>();
        request.add("signupKey", KeyFactory.generateSignupKey());
        request.add("expireKey", LocalDateTime.now().toString());
    }

    @Test
    @WithMockUser
    @DisplayName("가입 요청 인증 수락 API[POST] 테스트")
    void signupAccept() throws Exception {
        Long signupId = 1L;
        when(signupAcceptApplication.accept(any())).thenReturn(signupId);

        mockMvc.perform(post("/api/accept/signup")
                        .params(request)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.signupId").value(signupId));
        verify(signupAcceptApplication).accept(any());
    }
}