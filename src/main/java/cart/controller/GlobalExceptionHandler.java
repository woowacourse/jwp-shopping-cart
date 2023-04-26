package cart.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(DataAccessException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "데이터베이스에 접근할 수 없습니다.");

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(BindException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(errors);
    }
}
