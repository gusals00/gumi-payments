package flab.gumipayments.presentation;

import flab.gumipayments.application.AcceptCommand;
import flab.gumipayments.application.SignupAcceptApplication;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.ErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity signupAccept(@RequestBody AcceptInfoRequest acceptInfoRequest) {
        Long signupId = signupAcceptManagerApplication.accept(convert(acceptInfoRequest));
        return ResponseEntity.ok(new AcceptResponse(signupId));
    }

    private AcceptCommand convert(AcceptInfoRequest acceptInfoRequest) {
        return new AcceptCommand(acceptInfoRequest.getSignupKey());
    }

    @ExceptionHandler(value = SignupAcceptTimeoutException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(SignupAcceptTimeoutException e){
        return ExceptionResponse.of(ErrorCode.TIMEOUT, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    static class AcceptInfoRequest {
        @NotBlank
        private String signupKey;
    }

    @AllArgsConstructor
    @Getter
    static class AcceptResponse {
        private Long signupId;
    }
}
