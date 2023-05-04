package cart.controller;

import cart.controller.dto.ExceptionResponse;
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
public class ExceptionItemControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionItemControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundResultException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundResultException(NotFoundResultException exception) {
        log.warn(exception.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.badRequest()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(IllegalArgumentException exception) {
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
