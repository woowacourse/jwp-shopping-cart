package cart.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
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
        System.out.println(e.getMessage());
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        Map<String, List<String>> errorsResult = Map.of("errors", errors);

        return ResponseEntity.badRequest().body(errorsResult);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleAuthException(AuthException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalArgumentsException(IllegalArgumentException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        System.out.println(e.getMessage());
        return ResponseEntity.internalServerError().body("예기치 못한 에러가 발생했습니다.");
    }
}
