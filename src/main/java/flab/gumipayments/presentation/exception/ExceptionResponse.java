package flab.gumipayments.presentation.exception;

import flab.gumipayments.presentation.exception.ErrorCode.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Builder
@Getter
public class ExceptionResponse {
    private ErrorCode code;

    public ExceptionResponse(ErrorCode errorCode) {
        this.code = errorCode;
    }

    public static ResponseEntity<ExceptionResponse> exception(ErrorCode errorCode, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .code(errorCode)
                        .build());
    }
}
