package cart.controller;

import cart.controller.dto.ExceptionResponse;
import cart.exception.NotFoundResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// TODO: 2023/04/26 text/html을 기대할 떄와, application/json을 기대할 떄 똑같이 Json으로 예외 결과를 응답하는게 맞을까?
@ControllerAdvice
public class ExceptionItemControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionItemControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(badRequest.value(), badRequest.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(badRequest)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundResultException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundResultException(NotFoundResultException exception) {
        log.info(exception.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(badRequest.value(), badRequest.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(badRequest)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handlerIllegalArgumentException(IllegalArgumentException exception) {
        log.info(exception.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionResponse exceptionResponse = new ExceptionResponse(badRequest.value(), badRequest.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(badRequest)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception exception) {
        log.info(exception.getMessage());
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse exceptionResponse = new ExceptionResponse(internalServerError.value(), internalServerError.getReasonPhrase(), "예기치 못한 오류가 발생했습니다.");
        return ResponseEntity.status(internalServerError)
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(exceptionResponse);
    }
}
