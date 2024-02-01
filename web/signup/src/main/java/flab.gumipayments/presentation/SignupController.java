package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.signup.domain.SignupRequest;
import flab.gumipayments.apifirst.openapi.signup.rest.SignUpApi;
import flab.gumipayments.application.SignupCreateApplication;
import flab.gumipayments.domain.signup.SignupCreateCommand;
import flab.gumipayments.domain.signup.SignupIllegalStatusException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class SignupController implements SignUpApi {

    private final SignupCreateApplication signupRequesterApplication;

    // 가입요청
    @Override
    @PostMapping
    public ResponseEntity signup(@RequestBody @Valid SignupRequest request) {
        // 만료 기간
        LocalDateTime expireDate = createExpireDate(SIGNUP_KEY_EXPIRE_DAYS, SIGNUP_KEY_EXPIRE_HOURS, SIGNUP_KEY_EXPIRE_MINUTES);
        // 인증키 생성
        SignupCreateCommand signupCreateCommand = convertToSignupCommand(request, expireDate);

        signupRequesterApplication.signup(signupCreateCommand);

        return ResponseEntity.ok().build();
    }

    private SignupCreateCommand convertToSignupCommand(SignupRequest request, LocalDateTime expireDate) {
        return new SignupCreateCommand(request.getEmail(), expireDate);
    }

    private LocalDateTime createExpireDate(int days, int hours, int minutes) {
        return LocalDateTime.now()
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes);
    }

    @ExceptionHandler(value = SignupIllegalStatusException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(SignupIllegalStatusException e) {
        return ExceptionResponse.of(BusinessErrorCode.INVALID_STATUS, HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
