package cart.controller;

import cart.controller.dto.ExceptionResponse;
import cart.exception.ItemException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_ERROR_MESSAGE = "예상치 못한 예외가 발생했습니다.";
    private static final String NOT_READABLE_MESSAGE = "입력 타입을 확인하세요.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        fieldErrors.values()
                .forEach(logger::warn);

        return ResponseEntity.badRequest().body(new ExceptionResponse<>(fieldErrors));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        logger.warn(ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse<>(NOT_READABLE_MESSAGE));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException ex,
            final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {

        return ResponseEntity.badRequest().body(new ExceptionResponse<>("잘못된 입력입니다."));
    }

    @ExceptionHandler(ItemException.class)
    private ResponseEntity<ExceptionResponse<String>> handleItemException(ItemException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity.status(ex.getErrorStatus()).body(new ExceptionResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse<String>> handleException(Exception ex) {
        logger.warn("Exception : ", ex);
        return ResponseEntity.internalServerError().body(new ExceptionResponse<>(INTERNAL_ERROR_MESSAGE));
    }
}
