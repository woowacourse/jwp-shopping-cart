package cart.web.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cart.domain.admin.persistence.entity.ProductEntityMappingException;
import cart.domain.admin.service.DbNotAffectedException;

@ControllerAdvice
public class AdminApiExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ExceptionHandler
    public ResponseEntity<String> handleDataBindException(final MethodArgumentNotValidException exception) {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final String errorMessage = fieldErrors.stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("\n"));
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEntityMappingException(final ProductEntityMappingException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDbNotAffectedException(final DbNotAffectedException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(final Exception exception) {
        logger.error("Exception: " + exception.getMessage());
        return ResponseEntity.internalServerError().body("잠시 후 다시 시도해 주세요.");
    }
}
