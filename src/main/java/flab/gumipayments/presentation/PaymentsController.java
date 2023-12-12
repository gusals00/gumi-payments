package flab.gumipayments.presentation;

import flab.gumipayments.infrastructure.apikey.ApiKeyExpiredException;
import flab.gumipayments.infrastructure.apikey.ApiKeyInfo;
import flab.gumipayments.infrastructure.apikey.ApiKeyNotFoundException;
import flab.gumipayments.infrastructure.apikey.AuthenticatedApiKey;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @GetMapping
    public String test(@AuthenticatedApiKey ApiKeyInfo apiKeyInfo) {
        return apiKeyInfo.getApiKeyId() + " " + apiKeyInfo.getType().toString();
    }

    @ExceptionHandler(value = ApiKeyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(ApiKeyNotFoundException e) {
        return ExceptionResponse.of(NOT_FOUND, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = ApiKeyExpiredException.class)
    public ResponseEntity<ExceptionResponse> expiredExceptionHandler(ApiKeyExpiredException e) {
        return ExceptionResponse.of(EXPIRED, HttpStatus.UNAUTHORIZED, e.getMessage());
    }
}
