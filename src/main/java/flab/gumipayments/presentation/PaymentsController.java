package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.payments.domain.ApiKeyUseResponse;
import flab.gumipayments.apifirst.openapi.payments.rest.PaymentsApi;
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
public class PaymentsController implements PaymentsApi {

    @Override
    @GetMapping("/key-test")
    public ResponseEntity<ApiKeyUseResponse> paymentsApiKeyTest(ApiKeyInfo apiKeyInfo) {
        return ResponseEntity.ok(convert(apiKeyInfo));
    }


    @ExceptionHandler(value = ApiKeyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(ApiKeyNotFoundException e) {
        return ExceptionResponse.of(NOT_FOUND, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = ApiKeyExpiredException.class)
    public ResponseEntity<ExceptionResponse> expiredExceptionHandler(ApiKeyExpiredException e) {
        return ExceptionResponse.of(EXPIRED, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ApiKeyUseResponse convert(ApiKeyInfo apiKeyInfo) {
        return ApiKeyUseResponse.builder()
                .apiKeyType(apiKeyInfo.getType().name())
                .id(apiKeyInfo.getApiKeyId())
                .build();
    }
}
