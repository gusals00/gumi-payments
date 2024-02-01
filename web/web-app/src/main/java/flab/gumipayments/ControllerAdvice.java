package flab.gumipayments;


import flab.gumipayments.application.DuplicateSystemException;
import flab.gumipayments.application.NotFoundSystemException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.SystemErrorCode;
import flab.gumipayments.presentation.exceptionhandling.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
        return ExceptionResponse.of(BusinessErrorCode.TRY_AGAIN, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 바인딩 예외 처리
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResponse> bindExceptionHandler(BindException e) {
        return ExceptionResponse.of(BusinessErrorCode.BINDING, HttpStatus.BAD_REQUEST, e.getMessage());
    }


    // 시스템 예외 처리 시작
    @ExceptionHandler(value = DuplicateSystemException.class)
    public ResponseEntity<ExceptionResponse> duplicateSystemExceptionHandler(DuplicateSystemException e) {
        return ExceptionResponse.of(SystemErrorCode.DUPLICATED, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = NotFoundSystemException.class)
    public ResponseEntity<ExceptionResponse> systemExceptionHandler(NotFoundSystemException e) {
        return ExceptionResponse.of(SystemErrorCode.NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // 종료
}
