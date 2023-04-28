package cart.controller;

import cart.dto.ErrorCode;
import cart.dto.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessExceptionException(DataAccessException ex) {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_DATABASE_ACCESS), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindExceptionException(BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, fieldErrors), HttpStatus.BAD_REQUEST);
    }
}

