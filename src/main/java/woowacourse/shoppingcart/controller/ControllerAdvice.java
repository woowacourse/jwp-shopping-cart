package woowacourse.shoppingcart.controller;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.CommonExceptionDto;
import woowacourse.shoppingcart.dto.ValidationExceptionDto;
import woowacourse.shoppingcart.exception.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonExceptionDto> handleJwtException(final JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionDto> handleValidationException(final ValidationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ValidationExceptionDto(e.getField(), e.getMessage()));
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<CommonExceptionDto> handleCustomException(final CartException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new CommonExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonExceptionDto> handleUnhandledException(final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new CommonExceptionDto("Unhandled Exception"));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<CommonExceptionDto> handle() {
        return ResponseEntity.badRequest().body(new CommonExceptionDto("존재하지 않는 데이터 요청입니다."));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<CommonExceptionDto> handleInvalidRequest(final BindingResult bindingResult,
                                                                   final MethodArgumentNotValidException e) {
        e.printStackTrace();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(new CommonExceptionDto(mainError.getDefaultMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<CommonExceptionDto> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new CommonExceptionDto(e.getMessage()));
    }

    @ExceptionHandler({
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<CommonExceptionDto> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new CommonExceptionDto(e.getMessage()));
    }
}
