package cart.common;

import cart.excpetion.AuthenticationException;
import cart.excpetion.CartException;
import cart.excpetion.ProductionRepoException;
import cart.excpetion.ProductionServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handle(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({ProductionServiceException.class, ProductionRepoException.class})
    public ResponseEntity<String> handleProduct(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CartException.class})
    public ResponseEntity<String> handle(CartException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler()
    public ResponseEntity<String> handle(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.internalServerError().build();
    }
}
