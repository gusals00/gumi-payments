package flab.gumipayments.presentation;

import flab.gumipayments.application.SignupCreateApplication;
import flab.gumipayments.domain.signup.SignupCommand;
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
        SignupCommand signupInput = convertToSignupInput(request);
        signupRequesterApplication.signup(signupInput);

        return ResponseEntity.ok("");
    }

    private SignupCommand convertToSignupInput(SignupRequest request) {
        return new SignupCommand();
    }

    static class SignupRequest {
    }
}
