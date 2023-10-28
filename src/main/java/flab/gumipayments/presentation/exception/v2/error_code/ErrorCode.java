package flab.gumipayments.presentation.exception.v2.error_code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();
    HttpStatus getStatus();
    String name();
}