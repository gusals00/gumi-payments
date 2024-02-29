package flab.gumipayments.presentation;


import flab.gumipayments.apifirst.openapi.payments.domain.*;
import flab.gumipayments.apifirst.openapi.payments.rest.PaymentsApi;
import flab.gumipayments.application.PaymentProcessorApplication;
import flab.gumipayments.domain.ConfirmCommand;
import flab.gumipayments.infrastructure.apikey.*;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode.*;

@RestController
@RequiredArgsConstructor
public class PaymentsController implements PaymentsApi {

    private final PaymentProcessorApplication paymentProcessorApplication;

    @ApiKeyPairType(type = KeyPairType.SECRET_KEY)
    public ResponseEntity<ApiKeyUseResponse> paymentsApiKeyTest(ApiKeyInfo apiKeyInfo) {
        return ResponseEntity.ok(convert(apiKeyInfo));
    }

    @ApiKeyPairType(type = KeyPairType.CLIENT_KEY)
    @Override
    public ResponseEntity<PaymentProcessResponse> processPaymentRequest(ApiKeyInfo apiKeyInfo, PaymentRequest paymentRequest) {
        return PaymentsApi.super.processPaymentRequest(apiKeyInfo, paymentRequest);
    }

    @ApiKeyPairType(type = KeyPairType.CLIENT_KEY)
    @Override
    public ResponseEntity<RedirectResponse> acceptPaymentRequest(String key, Long paymentKey, Boolean isSuccess) {
        return PaymentsApi.super.acceptPaymentRequest(key, paymentKey, isSuccess);
    }

    @ApiKeyPairType(type = KeyPairType.SECRET_KEY)
    @Override
    public ResponseEntity<PaymentConfirmResponse> confirmPayment(ApiKeyInfo apiKeyInfo, PaymentConfirmRequest paymentConfirmRequest, String idempotencyKey) {
        return PaymentsApi.super.confirmPayment(apiKeyInfo, paymentConfirmRequest, idempotencyKey);
    }

    @ExceptionHandler(value = ApiKeyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(ApiKeyNotFoundException e) {
        return ExceptionResponse.of(NOT_FOUND, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = ApiKeyExpiredException.class)
    public ResponseEntity<ExceptionResponse> expiredExceptionHandler(ApiKeyExpiredException e) {
        return ExceptionResponse.of(EXPIRED, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = ApiKeyFormatException.class)
    public ResponseEntity<ExceptionResponse> formatExceptionHandler(ApiKeyFormatException e) {
        return ExceptionResponse.of(BINDING, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ApiKeyUseResponse convert(ApiKeyInfo apiKeyInfo) {
        return ApiKeyUseResponse.builder()
                .apiKeyType(apiKeyInfo.getType().name())
                .id(apiKeyInfo.getApiKeyId())
                .build();
    }

    private ConfirmCommand convertToConfirmCommand(ApiKeyInfo apiKeyInfo, PaymentConfirmRequest paymentConfirmRequest, String idempotencyKey) {
        return ConfirmCommand.builder()
                .apiKeyId(apiKeyInfo.getApiKeyId())
                .paymentKey(paymentConfirmRequest.getPaymentKey())
                .amount(paymentConfirmRequest.getAmount())
                .orderId(paymentConfirmRequest.getOrderId())
                .idempotencyKey(idempotencyKey)
                .build();
    }
}
