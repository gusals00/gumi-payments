package flab.gumipayments.presentation.exceptionhandling;

import flab.gumipayments.presentation.exceptionhandling.ErrorCode.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Builder
@Getter
public class ExceptionResponse {
    private ErrorCode code;
    private String message;

    public static ResponseEntity<ExceptionResponse> of(ErrorCode errorCode, HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .code(errorCode)
                        .message(message)
                        .build());
    }
}
