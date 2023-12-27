package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.apifirst.openapi.account.domain.AccountCreateRequest;
import flab.gumipayments.application.NotFoundSystemException;
import flab.gumipayments.application.account.AccountCreateManagerApplication;
import flab.gumipayments.domain.account.Account;
import flab.gumipayments.domain.signup.SignupIllegalStatusException;
import flab.gumipayments.infrastructure.config.WebMvcConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static flab.gumipayments.domain.account.Account.*;
import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {AccountController.class},
        excludeFilters =
                {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebMvcConfig.class})}
)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountCreateManagerApplication accountCreateManagerApplication;

    private String body;

    @Autowired
    private ObjectMapper mapper;

    private AccountCreateRequest request;
    private Long signupId;
    private AccountBuilder accountBuilder;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        request = new AccountCreateRequest("password123", "김현민");
        body = mapper.writeValueAsString(request);

        accountBuilder = Account.builder();
        signupId = 1L;
    }

    @Test
    @WithMockUser
    @DisplayName("성공: 계정 생성을 성공한다.")
    void createAccount() throws Exception {
        when(accountCreateManagerApplication.create(any(), any())).thenReturn(accountBuilder.build());

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 가입 요청이 존재하지 않으면 계정 생성이 실패한다.")
    void notExistSignup() throws Exception {
        when(accountCreateManagerApplication.create(any(), any())).thenThrow(new NotFoundSystemException());

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isInternalServerError())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(NotFoundSystemException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 계정이 이미 존재하면 계정 생성은 실패한다.")
    void alreadyExistAccount() throws Exception {
        when(accountCreateManagerApplication.create(any(), any())).thenThrow(new SignupIllegalStatusException());

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(SignupIllegalStatusException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 인증되지 않았으면 계정 생성은 실패한다.")
    void notAcceptedSignup() throws Exception {
        when(accountCreateManagerApplication.create(any(), any())).thenThrow(new SignupIllegalStatusException());

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(SignupIllegalStatusException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 비밀번호가 영어와 숫자를 포함한 6~12자리 이내가 아닌 경우 실패한다.")
    void WrongPasswordForm() throws Exception {
        request.setPassword("a");
        body = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(MethodArgumentNotValidException.class)
                );
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이름이 2자 이상 16자 이하가 아닌 경우 실패한다.")
    void WrongNameForm() throws Exception {
        request.setName("김");
        body = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/account/{signupId}", signupId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(MethodArgumentNotValidException.class)
                );
    }
}