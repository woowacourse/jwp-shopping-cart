package cart.controller;

import cart.ui.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버오류");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handler(AuthorizationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("권한이 없습니다.");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handler(IllegalStateException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handler(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder errMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errMessage.append("[")
                    .append(error.getField())
                    .append("] ")
                    .append(":")
                    .append(error.getDefaultMessage());
        }

        return ResponseEntity.badRequest()
                .body(errMessage.toString());
    }
}
