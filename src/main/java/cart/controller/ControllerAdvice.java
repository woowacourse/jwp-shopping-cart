package cart.controller;

import cart.controller.dto.ExceptionResponse;
import cart.exception.ItemException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("MethodArgumentNotValid : {}", ex);

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(new ExceptionResponse<>(fieldErrors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.info("HttpMessageNotReadable : ", ex);

        return ResponseEntity.badRequest().body(new ExceptionResponse<>("다시 접속해주세요."));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("MissingPathVariable : ", ex);

        return ResponseEntity.badRequest().body(new ExceptionResponse<>("존재하지 않는 상품입니다."));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        logger.info("TypeMismatch : ", ex);

        return ResponseEntity.badRequest().body(new ExceptionResponse<>("잘못된 경로입니다."));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException ex,
            final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("HttpRequestMethodNotSupported : ", ex);

        return ResponseEntity.badRequest().body(new ExceptionResponse<>("정상적인 경로로 다시 시도해주세요."));
    }

    @ExceptionHandler(ItemException.class)
    private ResponseEntity<ExceptionResponse<String>> handleItemException(ItemException ex) {
        logger.info(ex.getMessage());

        return ResponseEntity.status(ex.getErrorStatus()).body(new ExceptionResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse<String>> handleException(Exception ex) {
        logger.warn("Exception : ", ex);

        return ResponseEntity.internalServerError().body(new ExceptionResponse<>("예상치 못한 문제가 발생했습니다."));
    }
}
