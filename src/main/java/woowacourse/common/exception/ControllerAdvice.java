package woowacourse.common.exception;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.shoppingcart.dto.response.ErrorResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnhandledException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("예상치 못한 문제가 발생했습니다."));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final MethodArgumentNotValidException bindingResult) {
        String causes = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        String errorMessage = String.format("입력이 잘못되었습니다: [%s]", causes);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errorMessage));
    }

    // TODO: legacy: should be removed at step2
    @ExceptionHandler({
            EmptyResultDataAccessException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            InvalidCustomerException.class,
            InvalidCartItemException.class,
            InvalidProductException.class,
            InvalidOrderException.class,
            NotInCustomerCartItemException.class,
    })
    public ResponseEntity handleInvalidAccess(final RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private ErrorResponse toErrorResponse(Exception e) {
        return new ErrorResponse(e.getMessage());
    }
}
