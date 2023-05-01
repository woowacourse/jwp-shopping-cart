package cart.web.handler;

import cart.web.controller.dto.ErrorResponse;
import cart.web.exception.ErrorCode;
import cart.web.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> globalException(final GlobalException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = new ErrorResponse(errorCode, List.of(errorCode.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final List<String> errorMessage = getErrorMessage(e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST, errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unExpectedException(final Exception e) {
        log.error("error", e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNEXPECTED_EXCEPTION, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> getErrorMessage(final MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
