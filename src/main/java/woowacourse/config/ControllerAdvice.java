package woowacourse.config;

import java.util.List;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.exception.CustomerNotFoundException;
import woowacourse.exception.EmailDuplicateException;
import woowacourse.exception.InvalidCartItemException;
import woowacourse.exception.InvalidCustomerException;
import woowacourse.exception.InvalidOrderException;
import woowacourse.exception.InvalidProductException;
import woowacourse.exception.LoginFailureException;
import woowacourse.exception.NotInCustomerCartItemException;
import woowacourse.exception.PasswordIncorrectException;
import woowacourse.exception.TokenInvalidException;
import woowacourse.exception.UnauthorizedException;
import woowacourse.shoppingcart.ui.dto.response.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({LoginFailureException.class, EmailDuplicateException.class, PasswordIncorrectException.class,
            CustomerNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleLoginFailureException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<ExceptionResponse> handleTokenInvalidException(TokenInvalidException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleUnhandledException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("서버 내부 오류가 발생하였습니다."));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handle() {
        return ResponseEntity.badRequest().body(new ExceptionResponse("존재하지 않는 데이터 요청입니다."));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(new ExceptionResponse(mainError.getDefaultMessage()));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity<ExceptionResponse> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }
}
