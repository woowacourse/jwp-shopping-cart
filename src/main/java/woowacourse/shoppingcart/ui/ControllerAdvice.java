package woowacourse.shoppingcart.ui;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.ErrorResponse;
import woowacourse.shoppingcart.exception.ShoppingCartException;

@RestControllerAdvice
public class ControllerAdvice {

    private static final ErrorResponse INTERNAL_SERVER_RESPONSE = new ErrorResponse("997", "서버가 요청을 처리할 수 없습니다.");

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        final FieldError mainError = fieldErrors.get(0);

        return ResponseEntity.badRequest().body(new ErrorResponse("1000", mainError.getDefaultMessage()));
    }

    @ExceptionHandler({
            ShoppingCartException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidAccess(final ShoppingCartException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.toErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(final Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(INTERNAL_SERVER_RESPONSE);
    }
}
