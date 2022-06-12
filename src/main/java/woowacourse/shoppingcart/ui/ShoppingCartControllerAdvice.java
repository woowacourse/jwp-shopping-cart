package woowacourse.shoppingcart.ui;

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

import woowacourse.shoppingcart.exception.ShoppingCartException;
import woowacourse.shoppingcart.exception.domain.NotFoundException;
import woowacourse.shoppingcart.ui.dto.ExceptionResponse;

@RestControllerAdvice
public class ShoppingCartControllerAdvice {

    @ExceptionHandler(ShoppingCartException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
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
        return ResponseEntity.badRequest().body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExceptionResponse> handle() {
        return ResponseEntity.badRequest().body(new ExceptionResponse("존재하지 않는 데이터 요청입니다."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleUnhandledException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ExceptionResponse("알 수 없는 에러입니다."));
    }
}
