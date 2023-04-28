package cart.controller;

import cart.controller.dto.ErrorResponseDto;
import cart.exception.NoSuchProductException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice(assignableTypes = ProductsApiController.class)
public class ProductControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto validHandler(
            final MethodArgumentNotValidException exception,
            final HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        final String message = exception.getAllErrors().get(0).getDefaultMessage();
        return new ErrorResponseDto(message);
    }

    @ExceptionHandler(NoSuchProductException.class)
    public ErrorResponseDto noProductDataHandler(
            final NoSuchProductException exception,
            final HttpServletResponse httpServletResponse
    ) {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        final String message = exception.getMessage();
        return new ErrorResponseDto(message);
    }
}
