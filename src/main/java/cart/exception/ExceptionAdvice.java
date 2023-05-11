package cart.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(final Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.internalServerError()
            .body(new ErrorResponseDto("의도치 않은 서버 오류가 발생했습니다."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final IllegalArgumentException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedCustomerException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final UnAuthorizedCustomerException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(UnsupportedAuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final UnsupportedAuthenticationException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final MethodArgumentNotValidException exception) {
        final StringBuilder stringBuilder = new StringBuilder();
        final BindingResult bindingResult = exception.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append("[");
            stringBuilder.append(fieldError.getField());
            stringBuilder.append("](은)는 ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(" 입력된 값 : [");
            stringBuilder.append(fieldError.getRejectedValue());
            stringBuilder.append("]");
        }

        logger.error(stringBuilder.toString());
        return ResponseEntity.badRequest()
            .body(new ErrorResponseDto(stringBuilder.toString()));
    }
}
