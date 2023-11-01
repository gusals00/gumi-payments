package flab.gumipayments.presentation;

import flab.gumipayments.application.SignupCreateApplication;
import flab.gumipayments.domain.KeyFactory;
import flab.gumipayments.domain.signup.SignupCreateCommand;
import flab.gumipayments.domain.signup.exception.IllegalSignupStatusException;
import flab.gumipayments.domain.signup.exception.SignupAcceptTimeoutException;
import flab.gumipayments.presentation.exception.ErrorCode.ErrorCode;
import flab.gumipayments.presentation.exception.ExceptionResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static flab.gumipayments.application.Expire.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
@Slf4j
public class SignupController {

    private final SignupCreateApplication signupRequesterApplication;

    // 가입요청
    @PostMapping
    public ResponseEntity signup(@RequestBody @Valid SignupRequest request) {
        // 만료 기간
        LocalDateTime expireDate = createExpireDate(SIGNUP_KEY_EXPIRE_DAYS, SIGNUP_KEY_EXPIRE_HOURS, SIGNUP_KEY_EXPIRE_MINUTES);
        // 인증키 생성
        String signupKey = KeyFactory.generateSignupKey();
        SignupCreateCommand signupCreateCommand = convertToSignupCommand(request,expireDate,signupKey);

        signupRequesterApplication.signup(signupCreateCommand);

        return ResponseEntity.ok().build();
    }

    private SignupCreateCommand convertToSignupCommand(SignupRequest request,LocalDateTime expireDate, String signupKey) {
        return new SignupCreateCommand(request.getEmail(),expireDate,signupKey);
    }

    private LocalDateTime createExpireDate(int days,int hours,int minutes){
        return LocalDateTime.now()
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes);
    }

    @ExceptionHandler(value = IllegalSignupStatusException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(IllegalSignupStatusException e){
        return ExceptionResponse.exception(ErrorCode.INVALID_STATUS, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SignupAcceptTimeoutException.class)
    public ResponseEntity<ExceptionResponse> signupAcceptTimeoutException(SignupAcceptTimeoutException e){
        return ExceptionResponse.exception(ErrorCode.TIMEOUT, HttpStatus.BAD_REQUEST);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class SignupRequest {
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
    }

    @AllArgsConstructor
    @Getter
    static class Message{
        private String message;
    }
}
