package cart.exception;

import cart.controller.dto.ExceptionResponse;
import cart.exception.DataBaseSearchException;
import cart.exception.ItemException;
import cart.exception.ItemNotFoundException;
import cart.exception.NameRangeException;
import cart.exception.PriceRangeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "서버에서 예상치 못한 문제가 발생했습니다.";
    private static final String INPUT_NOT_READABLE_MESSAGE = "잘못된 입력입니다. 입력을 확인하세요.";
    private static final String PATH_VARIABLE_ERROR_MESSAGE = "요청 URI 형식이 잘못되었습니다.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(INPUT_NOT_READABLE_MESSAGE));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(INPUT_NOT_READABLE_MESSAGE));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(PATH_VARIABLE_ERROR_MESSAGE));
    }

    @ExceptionHandler({NameRangeException.class, PriceRangeException.class})
    private ResponseEntity<ExceptionResponse> handleItemException(ItemException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    private ResponseEntity<ExceptionResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(DataBaseSearchException.class)
    private ResponseEntity<ExceptionResponse> handleDataBaseSearchException(DataBaseSearchException ex) {
        logger.warn(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        logger.warn(ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(new ExceptionResponse(INTERNAL_ERROR_MESSAGE));
    }
}
