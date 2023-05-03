package cart.controller.advice;

import cart.controller.CartController;
import cart.controller.CartUserController;
import cart.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice(assignableTypes = {CartController.class, CartUserController.class})
public class CartControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(final NoSuchElementException exception) {
        final String errorMessage = exception.getMessage();
        logger.error("error : {}", errorMessage);

        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(errorMessage));
    }
}
