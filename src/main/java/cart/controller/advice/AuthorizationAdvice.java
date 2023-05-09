package cart.controller.advice;

import cart.controller.dto.ExceptionResponse;
import cart.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorizationAdvice {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationAdvice.class);

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException exception) {
        log.info(exception.getMessage());
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                unauthorized.value(),
                unauthorized.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.status(unauthorized)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }
}
