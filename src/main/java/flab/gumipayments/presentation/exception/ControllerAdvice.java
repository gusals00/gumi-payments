package flab.gumipayments.presentation.exception;


import flab.gumipayments.presentation.exception.v1.DuplicateException;
import flab.gumipayments.presentation.exception.v1.ErrorRes1;
import flab.gumipayments.presentation.exception.v2.CustomException;
import flab.gumipayments.presentation.exception.v2.ErrorRes2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    //exception v1
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorRes1 illegalArgsExHandle(IllegalArgumentException ex) {
        return new ErrorRes1(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateException.class)
    public ErrorRes1 duplicateExHandle(DuplicateException ex) {
        return new ErrorRes1(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    // exception v2
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorRes2> handleCustomException(CustomException e) {
        log.error("[exception] class={}, code ={}, status = {}, message = {}",
                e.getErrorCode().getClass().getName(), e.getErrorCode().name(), e.getErrorCode().getStatus(), e.getErrorCode().getMessage(), e);
        return ErrorRes2.error(e);
    }
}
