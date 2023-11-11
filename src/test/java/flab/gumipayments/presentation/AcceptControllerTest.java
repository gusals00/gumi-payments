package flab.gumipayments.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.gumipayments.application.SignupAcceptApplication;
import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AcceptController.class)
@AutoConfigureRestDocs
class AcceptControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @MockBean
    private SignupAcceptApplication signupAcceptApplication;

    private MultiValueMap<String, String> request;

    @BeforeEach
    void setUp() {
        request = new LinkedMultiValueMap<>();
        request.add("signupKey", KeyFactory.generateSignupKey());
    }

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
                .andExpect(jsonPath("$.signupId").value(signupId))

                .andDo(document("accept/signup_accept",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("signupKey").type(JsonFieldType.STRING)
                                        .description("인증한 가입 요청의 signupKey")
                        ),
                        responseFields(
                                fieldWithPath("signupId").type(JsonFieldType.NUMBER)
                                        .description("인증한 가입 요청의 id")
                        )
                ));
    }

    private String acceptRequestBody(String signupKey) throws JsonProcessingException {
        return mapper.writeValueAsString(new AcceptController.AcceptInfoRequest(signupKey));
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
        when(signupAcceptApplication.accept(any())).thenThrow(new NoSuchElementException());

        mockMvc.perform(post("/api/accept/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(acceptRequestBody("1234"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(NoSuchElementException.class)
                );
    }
}