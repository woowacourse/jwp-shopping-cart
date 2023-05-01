package cart.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public final class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(final Exception e) {
        logger.error(e.getMessage());
        logger.error(e.getClass().toString());
        return ResponseEntity.internalServerError().body(new ErrorResponseDto("알 수 없는 서버 예외가 발생하였습니다."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final IllegalArgumentException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(final MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        final StringBuilder stringBuilder = new StringBuilder();

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
