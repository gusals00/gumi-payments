package flab.gumipayments.presentation;


import flab.gumipayments.apifirst.openapi.payments.domain.*;
import flab.gumipayments.apifirst.openapi.payments.rest.PaymentsApi;
import flab.gumipayments.application.*;
import flab.gumipayments.domain.*;
import flab.gumipayments.infrastructure.apikey.*;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.AcceptErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentsController implements PaymentsApi {

    private final PaymentProcessorApplication paymentProcessorApplication;
    private final PaymentRequesterApplication paymentRequesterApplication;
    private final RequestCommandCreatorApplication requestCommandCreatorApplication;
    private final PaymentAcceptRequester paymentAcceptRequester;


    @ApiKeyPairType(type = KeyPairType.SECRET_KEY)
    @Override
    public ResponseEntity<ApiKeyUseResponse> paymentsApiKeyTest(ApiKeyInfo apiKeyInfo) {
        return ResponseEntity.ok(convert(apiKeyInfo));
    }

    @ApiKeyPairType(type = KeyPairType.CLIENT_KEY)
    @Override
    public ResponseEntity<PaymentProcessResponse> processPaymentRequest(ApiKeyInfo apiKeyInfo, PaymentRequest paymentRequest) {
        RequestCommand requestCommand = requestCommandCreatorApplication.getRequestCommand(convert(paymentRequest, apiKeyInfo));

        String paymentUrl = paymentRequesterApplication.requestPayment(requestCommand);
        return ResponseEntity.ok(PaymentProcessResponse.builder().paymentUrl(paymentUrl).build());
    }

    @ApiKeyPairType(type = KeyPairType.CLIENT_KEY)
    @Override
    public ResponseEntity<RedirectResponse> acceptPaymentRequest(String key, String paymentKey, Boolean isSuccess) {
        String redirectUrl = paymentAcceptRequester.authenticate(paymentKey, key, isSuccess);

        return ResponseEntity.ok(RedirectResponse.builder()
                .redirectUrl(redirectUrl)
                .build());
    }

    @ApiKeyPairType(type = KeyPairType.SECRET_KEY)
    @Override
    public ResponseEntity<PaymentConfirmResponse> confirmPayment(ApiKeyInfo apiKeyInfo, PaymentConfirmRequest paymentConfirmRequest, String idempotencyKey) {
        return PaymentsApi.super.confirmPayment(apiKeyInfo, paymentConfirmRequest, idempotencyKey);
    }

    // 인증사 인증 실패시 전달 url 시작
    @ExceptionHandler(value = AuthenticatorAcceptException.class)
    public ResponseEntity<FailUrlResponse> authenticatorAcceptExceptionHandler(AuthenticatorAcceptException e) {
        return FailUrlResponse.of(AcceptErrorCode.AUTHENTICATOR, e.getMessage(), e.getFailUrl(), e.getOrderId());
    }

    @ExceptionHandler(value = PaymentAcceptException.class)
    public ResponseEntity<FailUrlResponse> paymentAcceptExceptionHandler(AuthenticatorAcceptException e) {
        return FailUrlResponse.of(AcceptErrorCode.PAYMENT, e.getMessage(), e.getFailUrl(), e.getOrderId());
    }

    // 종료
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

    private PaymentRequestCreateCommand convert(PaymentRequest paymentRequest, ApiKeyInfo apiKeyInfo) {
        return PaymentRequestCreateCommand.builder()
                .orderId(paymentRequest.getOrderId())
                .orderName(paymentRequest.getOrderName())
                .customerEmail(paymentRequest.getCustomerEmail())
                .customerName(paymentRequest.getCustomerName())
                .amount(paymentRequest.getAmount())
                .cardCompany(paymentRequest.getCardCompany())
                .successUrl(paymentRequest.getSuccessUrl())
                .failUrl(paymentRequest.getFailUrl())
                .apiKeyId(apiKeyInfo.getApiKeyId())
                .build();
    }
}
