package cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cart.exception.DomainException;
import cart.exception.ExceptionCode;

@ControllerAdvice
public class JwpCartControllerAdvice {

    @ResponseBody
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ExceptionCode> handleDomainException(DomainException e) {
        return ResponseEntity.badRequest().body(e.getExceptionCode());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionCode> handleBadRequest(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ExceptionCode.BAD_REQUEST);
    }
}
