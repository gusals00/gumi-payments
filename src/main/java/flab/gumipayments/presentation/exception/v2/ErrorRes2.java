package flab.gumipayments.presentation.exception.v2;

import flab.gumipayments.presentation.exception.v2.error_code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@Builder
public class ErrorRes2 {

    private final HttpStatus status;
    private final String code;
    private final String msg;

    public ErrorRes2(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.name();
        this.msg = errorCode.getMessage();
    }

    public static ResponseEntity<ErrorRes2> error(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorRes2.builder()
                        .status(e.getErrorCode().getStatus())
                        .code(e.getErrorCode().name())
                        .msg(e.getErrorCode().getMessage())
                        .build());
    }

    public static ResponseEntity<ErrorRes2> error(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorRes2.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.name())
                        .msg(message)
                        .build());
    }

    public static ResponseEntity<ErrorRes2> error(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorRes2.builder()
                        .status(errorCode.getStatus())
                        .code(errorCode.name())
                        .msg(errorCode.getMessage())
                        .build());
    }
}

