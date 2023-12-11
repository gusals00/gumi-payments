package flab.gumipayments.presentation;

import flab.gumipayments.infrastructure.ApiKeyNotFoundException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    @GetMapping
    public String test() {
        return "hello!!";
    }

    @ExceptionHandler(value = ApiKeyNotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(ApiKeyNotFoundException e) {
        return ExceptionResponse.of(BusinessErrorCode.NOT_FOUND, HttpStatus.UNAUTHORIZED, e.getMessage());
    }
}
