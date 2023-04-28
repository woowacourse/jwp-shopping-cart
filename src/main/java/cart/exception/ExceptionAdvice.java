package cart.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final IllegalArgumentException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponseDto(exception.getMessage()));
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
        return ResponseEntity.badRequest().body(new ErrorResponseDto(stringBuilder.toString()));
    }
}
