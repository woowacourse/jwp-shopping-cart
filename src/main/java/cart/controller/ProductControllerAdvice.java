package cart.controller;

import cart.controller.dto.ErrorResponse;
import cart.exception.NoSuchProductException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice(assignableTypes = ProductsApiController.class)
public class ProductControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validHandler(
            final MethodArgumentNotValidException exception,
            final HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        final String message = exception.getAllErrors().get(0).getDefaultMessage();
        return new ErrorResponse(message);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ErrorResponse noProductDataHandler(
            final NoSuchProductException exception,
            final HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        final String message = exception.getMessage();
        return new ErrorResponse(message);
    }
}
