package cart.controller;

import cart.annotation.login.LoginException;
import cart.dto.ErrorCode;
import cart.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessExceptionException(DataAccessException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_DATABASE_ACCESS), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindExceptionException(BindException ex) {
        log.error(ex.getMessage());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, fieldErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginExceptionException(LoginException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), 401), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentExceptionException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

