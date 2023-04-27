package cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, DataAccessException.class})
    public ResponseEntity<String> handle(final Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final Map<String, String> errorMessageByFields = new HashMap<>();
        final List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();

        allErrors.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessageByFields.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(errorMessageByFields);
    }
}
