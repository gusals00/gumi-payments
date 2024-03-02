package flab.gumipayments.presentation;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.AcceptErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@Builder
public class FailUrlResponse {

    private String failUrl;

    @Builder
    public FailUrlResponse(String failUrl) {
        this.failUrl = failUrl;
    }

    public static ResponseEntity<FailUrlResponse> of(AcceptErrorCode errorCode, String message, String failUrl, String orderId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(FailUrlResponse.builder()
                        .failUrl(failUrl+"?code="+errorCode+"&message="+message+"&orderId="+orderId)
                        .build());
    }
}
