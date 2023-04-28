package cart.config;

import cart.exception.DBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream().map(ex -> ex.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, List<String>> errorsResult = Map.of("errors", errors);

        return ResponseEntity.badRequest().body(errorsResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDBException(DBException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalArgumentsException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
