package flab.gumipayments.presentation;

import flab.gumipayments.application.SignupCreateApplication;
import flab.gumipayments.domain.signup.SignupCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/signup")
public class SignupController {

    private final SignupCreateApplication signupRequesterApplication;

    // 가입요청
    @PostMapping
    public ResponseEntity signup(SignupRequest request) {
        SignupCommand signupCommand = convertToSignupCommand(request);
        signupRequesterApplication.signup(signupCommand);

        return ResponseEntity.ok("");
    }

    private SignupCommand convertToSignupCommand(SignupRequest request) {
        return new SignupCommand(request.getEmail(), request.getPassword(), request.getName());
    }

    @AllArgsConstructor
    @Getter
    static class SignupRequest {
        private String email;
        private String password;
        private String name;
    }
}
