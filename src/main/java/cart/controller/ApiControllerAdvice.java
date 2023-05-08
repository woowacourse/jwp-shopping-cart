package cart.controller;

import cart.controller.dto.ErrorResponse;
import cart.exception.TableIdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {ProductsApiController.class, CartApiController.class})
public class ApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validHandler(final MethodArgumentNotValidException exception) {
        final String message = exception.getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(TableIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> noProductDataHandler(final TableIdNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
