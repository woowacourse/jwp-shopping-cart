package cart.web.handler;

import cart.exception.GlobalException;
import cart.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> globalException(final GlobalException e) {
        log.info("Error from GlobalException = ", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final String errorMessages = getErrorMessage(e);
        log.error("Error from MethodArgumentNotValidException = ", e);
        return new ResponseEntity<>(new ExceptionResponse(errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> unAuthorizedException(final UnAuthorizedException e) {
        log.error("Error from UnAuthorizedException = ", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> unExpectedException(final Exception e) {
        log.error("Error From UnExpectedException = ", e);
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getErrorMessage(final MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
    }
}
