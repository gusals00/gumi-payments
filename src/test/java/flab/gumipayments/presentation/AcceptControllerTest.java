package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.apifirst.openapi.accept.domain.AcceptInfoRequest;
import flab.gumipayments.application.NotFoundException;
import flab.gumipayments.application.signup.SignupAcceptApplication;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcceptController.class)
class AcceptControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @MockBean
    private SignupAcceptApplication signupAcceptApplication;

    @Test
    @WithMockUser
    @DisplayName("성공: 가입 요청 인증을 성공한다.")
    void signupAccept() throws Exception {
        Long signupId = 1L;

        when(signupAcceptApplication.accept(any())).thenReturn(signupId);

        mockMvc.perform(post("/api/accept/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody("1234"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.signupId").value(signupId));
    }

    private String acceptRequestBody(String signupKey) throws JsonProcessingException {
        return mapper.writeValueAsString(new AcceptInfoRequest(signupKey));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 기간이 만료된 인증은 실패한다.")
    void expiredAccept() throws Exception {
        when(signupAcceptApplication.accept(any())).thenThrow(new SignupAcceptTimeoutException());

        mockMvc.perform(post("/api/accept/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody("1234"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(SignupAcceptTimeoutException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 인증할 가입 요청이 존재하지 않으면 인증에 실패한다.")
    void notExistSignup() throws Exception {
        when(signupAcceptApplication.accept(any())).thenThrow(new NotFoundException(NOT_FOUND));

        mockMvc.perform(post("/api/accept/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody("1234"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(NotFoundException.class)
                );
    }
}