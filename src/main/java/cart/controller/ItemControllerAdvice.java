package cart.controller;

import cart.controller.dto.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ItemController.class)
public class ItemControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ItemControllerAdvice.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(final Exception exception) {
        log.info(exception.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                badRequest.value(),
                badRequest.getReasonPhrase(),
                exception.getMessage());
        return ResponseEntity.status(badRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(final Exception exception) {
        log.error(exception.getMessage());
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                internalServerError.value(),
                internalServerError.getReasonPhrase(),
                "예기치 못한 오류가 발생했습니다.");
        return ResponseEntity.status(internalServerError)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }
}
