package cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cart.exception.DomainException;
import cart.exception.ExceptionCode;

@RestControllerAdvice
public class JwpCartControllerAdvice {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionCode> handleDomainException(DomainException e) {
        return ResponseEntity.badRequest().body(e.getExceptionCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionCode> handleBadRequest(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ExceptionCode.BAD_REQUEST);
    }
}
