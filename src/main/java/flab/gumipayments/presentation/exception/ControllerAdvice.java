package flab.gumipayments.presentation.exception;


import flab.gumipayments.presentation.exception.ErrorCode.ErrorCode;
import flab.gumipayments.presentation.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandler(RuntimeException e){
        return ExceptionResponse.exception(ErrorCode.TRY_AGAIN,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElementExceptionHandler(NoSuchElementException e){
        return ExceptionResponse.exception(ErrorCode.NOT_FOUND,HttpStatus.BAD_REQUEST);
    }
}
