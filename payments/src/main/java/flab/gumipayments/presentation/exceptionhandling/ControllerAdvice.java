package flab.gumipayments.presentation.exceptionhandling;


import flab.gumipayments.presentation.exceptionhandling.ErrorCode.BusinessErrorCode;
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
    // 종료
}
