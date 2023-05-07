package cart.catalog.view;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleAll(final Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
