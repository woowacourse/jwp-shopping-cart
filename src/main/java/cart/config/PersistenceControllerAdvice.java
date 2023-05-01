package cart.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersistenceControllerAdvice {
    @ExceptionHandler(PersistenceException.class)
    public String sendMessage(PersistenceException exception) {
        return exception.getMessage();
    }
}
