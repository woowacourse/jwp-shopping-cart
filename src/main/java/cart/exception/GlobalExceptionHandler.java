package cart.exception;

import cart.controller.dto.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> globalException(final GlobalException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = new ErrorResponse(errorCode,
            List.of(errorCode.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(
        final MethodArgumentNotValidException e) {
        final List<String> errorMessage = getErrorMessage(e);
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST,
            errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizedException() {
        final ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        final ErrorResponse errorResponse = new ErrorResponse(errorCode,
            List.of(errorCode.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenException() {
        final ErrorCode errorCode = ErrorCode.FORBIDDEN;
        final ErrorResponse errorResponse = new ErrorResponse(errorCode,
            List.of(errorCode.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(final Exception e) {
        log.error(e.getMessage());
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = new ErrorResponse(errorCode,
            List.of(errorCode.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> getErrorMessage(final MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
    }
}
