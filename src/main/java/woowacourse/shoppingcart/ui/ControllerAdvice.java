package woowacourse.shoppingcart.ui;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.auth.exception.InvalidTokenException;
import woowacourse.auth.exception.LoginFailException;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.dto.ErrorResponseWithField;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.ForbiddenAccessException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerPropertyException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnhandledException() {
        return new ErrorResponse("Unhandled Exception");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle() {
        return new ErrorResponse("존재하지 않는 데이터 요청입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseWithField handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return new ErrorResponseWithField(mainError.getField(), mainError.getDefaultMessage());
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseWithField handleDuplicatedRequest(DuplicateCustomerException exception) {
        return new ErrorResponseWithField(exception.getField(), exception.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({LoginFailException.class, InvalidTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleLoginFailException(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenAccessException(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        ConstraintViolationException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidRequest(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
        InvalidCustomerPropertyException.class,
        InvalidCustomerException.class,
        InvalidCartItemException.class,
        InvalidProductException.class,
        NotInCustomerCartItemException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidAccess(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
