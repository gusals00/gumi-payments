package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.accept.domain.AcceptInfoRequest;
import flab.gumipayments.apifirst.openapi.accept.domain.AcceptResponse;
import flab.gumipayments.apifirst.openapi.accept.rest.AcceptApi;
import flab.gumipayments.application.AcceptCommand;
import flab.gumipayments.application.SignupAcceptApplication;
import flab.gumipayments.domain.signup.SignupAcceptTimeoutException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accept")
@Slf4j
public class AcceptController implements AcceptApi {

    private final SignupAcceptApplication signupAcceptManagerApplication;

    // 인증 수락
    @PostMapping("/signup")
    @Override
    public ResponseEntity<AcceptResponse> signupAccept(AcceptInfoRequest acceptInfoRequest) {
        Long signupId = signupAcceptManagerApplication.accept(convert(acceptInfoRequest));
        return ResponseEntity.ok(new AcceptResponse(signupId));
    }

    private AcceptCommand convert(AcceptInfoRequest acceptInfoRequest) {
        return new AcceptCommand(acceptInfoRequest.getSignupKey());
    }

    @ExceptionHandler(value = SignupAcceptTimeoutException.class)
    public ResponseEntity<ExceptionResponse> signupAcceptTimeoutExceptionHandler(SignupAcceptTimeoutException e) {
        return ExceptionResponse.of(BusinessErrorCode.TIMEOUT, HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
