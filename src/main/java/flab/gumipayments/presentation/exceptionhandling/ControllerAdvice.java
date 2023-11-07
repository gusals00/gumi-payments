package flab.gumipayments.presentation.exceptionhandling;


import flab.gumipayments.application.DuplicateException;
import flab.gumipayments.presentation.exceptionhandling.ErrorCode.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e){
        return ExceptionResponse.of(ErrorCode.TRY_AGAIN,HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException e){
        return ExceptionResponse.of(ErrorCode.NOT_FOUND,HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = DuplicateException.class)
    public ResponseEntity<ExceptionResponse> duplicateExceptionHandler(DuplicateException e){
        return ExceptionResponse.of(ErrorCode.DUPLICATED,HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResponse> bindExceptionHandler(BindException e) {
        return ExceptionResponse.of(ErrorCode.BINDING,HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
