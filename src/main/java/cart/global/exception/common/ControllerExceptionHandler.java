package cart.global.exception.common;

import cart.global.exception.auth.AuthorizationNotFoundException;
import cart.global.exception.auth.InvalidAuthorizationException;
import cart.global.exception.cart.ProductNotFoundInCartException;
import cart.global.exception.product.ProductNotFoundException;
import cart.global.exception.response.ErrorResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionStatus.BAD_INPUT_VALUE_EXCEPTION);
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        for (FieldError error : errors) {
            errorResponse.addValidation(error.getField(), error.getDefaultMessage());
        }

        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException() {
        return new ErrorResponse(ExceptionStatus.BAD_INPUT_VALUE_EXCEPTION);
    }

    @ExceptionHandler({InvalidAuthorizationException.class, AuthorizationNotFoundException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthorizationException(final CartException e) {
        return new ErrorResponse(e.getExceptionStatus());
    }

    @ExceptionHandler({ProductNotFoundException.class, ProductNotFoundInCartException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(final CartException e) {
        return new ErrorResponse(e.getExceptionStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException() {
        return new ErrorResponse(ExceptionStatus.INTERNAL_SERVER_EXCEPTION);
    }
}
