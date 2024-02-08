package flab.gumipayments.presentation;

import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyIssueRequest;
import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyIssueResponse;
import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyReIssueRequest;
import flab.gumipayments.apifirst.openapi.apikey.domain.ApiKeyReIssueResponse;
import flab.gumipayments.apifirst.openapi.apikey.rest.ApiKeyApi;
import flab.gumipayments.application.*;
import flab.gumipayments.domain.ApiKeyExtendException;
import flab.gumipayments.domain.ApiKeyPair;
import flab.gumipayments.domain.IssueFactor;
import flab.gumipayments.domain.ReIssueFactor;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/api-key")
public class ApiKeyController implements ApiKeyApi {

    private final ApiKeyIssueRequesterApplication issueRequesterApplication;
    private final ApiKeyReIssueRequesterApplication reIssueRequesterApplication;
    private final ApiKeyFactorCreatorApplication factorCreateService;

    public static final int KEY_EXTEND_YEAR = 2;

    // api 키 발급
    @PostMapping
    @Override
    public ResponseEntity<ApiKeyIssueResponse> issueApiKey(ApiKeyIssueRequest issueRequest) {

        // 발급 factor 생성
        IssueFactor issueFactor = factorCreateService.getIssueFactor(convert(issueRequest));

        // key 발급
        ApiKeyPair apiKeyPair = issueRequesterApplication.issueApiKey(issueFactor);

        return ResponseEntity.ok(convertToIssueResponse(apiKeyPair));
    }

    // api 키 재발급
    @PostMapping("/re")
    @Override
    public ResponseEntity<ApiKeyReIssueResponse> reIssueApiKey(ApiKeyReIssueRequest reIssueRequest) {

        //재발급 factor 생성
        ReIssueFactor reIssueFactor = factorCreateService.getReIssueFactor(convert(reIssueRequest));

        // key 재발급
        ApiKeyPair apiKeyPair = reIssueRequesterApplication.reIssueApiKey(reIssueFactor);

        return ResponseEntity.ok(convertToReIssueResponse(apiKeyPair));
    }

    @ExceptionHandler(value = ApiKeyIssueException.class)
    public ResponseEntity<ExceptionResponse> apiKeyIssueExceptionHandler(ApiKeyIssueException e) {
        return ExceptionResponse.of(INVALID_STATUS, BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = ApiKeyExtendException.class)
    public ResponseEntity<ExceptionResponse> apiKeyExtendExceptionHandler(ApiKeyExtendException e) {
        return ExceptionResponse.of(INVALID_EXTEND, BAD_REQUEST, e.getMessage());
    }

    private ApiKeyIssueResponse convertToIssueResponse(ApiKeyPair apiKeyPair) {
        return ApiKeyIssueResponse.builder()
                .clientKey(apiKeyPair.getClientKey())
                .secretKey(apiKeyPair.getSecretKey()).build();
    }

    private ApiKeyReIssueResponse convertToReIssueResponse(ApiKeyPair apiKeyPair) {
        return ApiKeyReIssueResponse.builder()
                .clientKey(apiKeyPair.getClientKey())
                .secretKey(apiKeyPair.getSecretKey()).build();
    }

    private ApiKeyIssueCreateCommand convert(ApiKeyIssueRequest request) {
        return ApiKeyIssueCreateCommand.builder()
                .accountId(request.getAccountId())
                .expireDate(LocalDateTime.now().plusYears(KEY_EXTEND_YEAR))
                .type(request.getKeyType()).build();
    }

    private ApiKeyReIssueCreateCommand convert(ApiKeyReIssueRequest request) {
        return ApiKeyReIssueCreateCommand.builder()
                .accountId(request.getAccountId())
                .expireDate(LocalDateTime.now().plusYears(KEY_EXTEND_YEAR))
                .type(request.getKeyType()).build();
    }
}
