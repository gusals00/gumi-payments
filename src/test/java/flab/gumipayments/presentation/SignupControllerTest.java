package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.application.DuplicateException;
import flab.gumipayments.application.SignupCreateApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;


import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.ErrorCode.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(SignupController.class)
@AutoConfigureRestDocs
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
        body = emailRequestBody("gusals@naver.com");
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
                .andExpect(status().isOk())
                .andDo(document("signup/signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description("회원 가입 시 이메일")
                        )
                ));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이미 생성한 계정이 존재하면 가입 요청은 실패한다.")
    void alreadyExistAccount() throws Exception {
        doThrow(new DuplicateException()).when(signupCreateApplication).signup(any());

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(DUPLICATED.name()));
    }


    @Test
    @WithMockUser
    @DisplayName("예외: 이메일 형식이 비어있는 상태로 요청할 경우 가입 요청은 실패한다.")
    void signupInvalidEmailException1() throws Exception {
        body = emailRequestBody("");

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이메일 형식에 @가 없는 상태로 요청할 경우 가입 요청은 실패한다.")
    void signupInvalidEmailException2() throws Exception {
        body = emailRequestBody("love470");

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이메일 형식이 @뒤에 내용이 없는 상태로 요청할 경우 가입 요청은 실패한다.")
    void signupInvalidEmailException3() throws Exception {
        body = emailRequestBody("love4702@");

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이메일 형식이 @뒤에 점(.)이 없는 상태로 요청할 경우 가입 요청은 실패한다.")
    void signupInvalidEmailException4() throws Exception {
        body = emailRequestBody("love470@naver");

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    @Test
    @WithMockUser
    @DisplayName("예외: 이메일 형식이 점(.)뒤에 최상위 도메인이 없는 상태로 요청할 경우 가입 요청은 실패한다.")
    void signupInvalidEmailException5() throws Exception {
        body = emailRequestBody("love470@naver@");

        mockMvc.perform(post("/api/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BINDING.name()));
    }

    private String emailRequestBody(String email) throws JsonProcessingException {
        return mapper.writeValueAsString(new SignupController.SignupRequest(email));
    }


}