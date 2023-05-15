package cart.controller;

import cart.auth.interceptor.AuthorizationException;
import cart.controller.dto.response.ExceptionResponse;
import cart.exception.NotFoundResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionItemAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionItemAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, AuthorizationException.class})
    public ResponseEntity<ExceptionResponse> handleClientException(Exception exception) {
        log.info(exception.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler({NotFoundResultException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleWarnException(Exception exception) {
        log.warn(exception.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception exception) {
        log.error(exception.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "예기치 못한 오류가 발생했습니다.");
        return ResponseEntity.internalServerError()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }
}
