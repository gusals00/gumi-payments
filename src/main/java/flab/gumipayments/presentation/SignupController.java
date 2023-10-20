package flab.gumipayments.presentation;

import flab.gumipayments.application.SignupCreateApplication;
import flab.gumipayments.domain.signup.SignupCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
@Slf4j
public class SignupController {

    private final SignupCreateApplication signupRequesterApplication;

    // 가입요청
    @PostMapping
    public ResponseEntity signup(@RequestBody @Valid SignupRequest request) {
        SignupCommand signupCommand = convertToSignupCommand(request);
        signupRequesterApplication.signup(signupCommand);

        return ResponseEntity.ok(new SignupResponse("이메일을 확인해주세요"));
    }

    private SignupCommand convertToSignupCommand(SignupRequest request) {
        return new SignupCommand(request.getEmail(), request.getPassword(), request.getName());
    }

    @NoArgsConstructor
    @Getter
    static class SignupRequest {
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
        @Pattern(regexp = "[a-zA-Z0-9]{6,12}",message = "비밀번호는 영어와 숫자를 포함한 6~12자리 이내로 입력해주세요.")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
        @NotBlank(message = "이름을 입력해주세요")
        private String name;
    }

    @AllArgsConstructor
    @Getter
    static class SignupResponse{
        private String message;

    }
}
