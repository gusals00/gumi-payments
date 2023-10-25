package flab.gumipayments.presentation;

import flab.gumipayments.application.AcceptCommand;
import flab.gumipayments.application.SignupAcceptApplication;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accept")
@Slf4j
public class AcceptController {

    private final SignupAcceptApplication signupAcceptManagerApplication;

    // 인증 수락
    @PostMapping("/signup")
    public ResponseEntity signupAccept(AcceptInfoRequest acceptInfoRequest) {
        Long signupId = signupAcceptManagerApplication.accept(convert(acceptInfoRequest));
        return ResponseEntity.ok(new AcceptResponse(signupId));
    }

    private AcceptCommand convert(AcceptInfoRequest acceptInfoRequest) {
        return new AcceptCommand(acceptInfoRequest.getSignupKey(), acceptInfoRequest.getEmail());
    }

    @NoArgsConstructor
    @Getter
    @Setter
    static class AcceptInfoRequest {
        @NotBlank
        private String signupKey;
        @Email
        private String email;
    }

    @AllArgsConstructor
    @Getter
    static class AcceptResponse {
        private Long signupId;
    }
}
