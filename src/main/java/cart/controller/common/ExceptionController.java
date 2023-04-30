package cart.controller.common;

import cart.dto.response.ErrorResponse;
import cart.dto.response.Response;
import cart.dto.response.SimpleResponse;
import cart.exception.AuthenticationException;
import cart.exception.CartNotFoundException;
import cart.exception.DuplicateEmailException;
import cart.exception.ProductException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
    private static final String INVALID_REQUEST_MESSAGE = "잘못된 요청입니다.";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response> handleRuntimeException(RuntimeException e) {
        log.error("알 수 없는 문제가 발생했습니다.", e);
        return ResponseEntity.internalServerError()
                .body(SimpleResponse.internalServerError("알 수 없는 문제가 발생했습니다."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.badRequest(INVALID_REQUEST_MESSAGE);
        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<Response> handleProductException(ProductException e) {
        Response response = SimpleResponse.badRequest(e.getMessage());
        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Response> handleDuplicateEmailException(DuplicateEmailException e) {
        ErrorResponse errorResponse = ErrorResponse.badRequest(INVALID_REQUEST_MESSAGE);
        errorResponse.addValidation("email", e.getMessage());
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.info("인증 요청이 실패 했습니다. 사유: {} TOKEN = {}", e.getMessage(), request.getHeader("Authorization"));
        Response response = new SimpleResponse("401", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = ErrorResponse.badRequest(INVALID_REQUEST_MESSAGE);
        errorResponse.addValidation(e.getName(), "유효하지 않은 경로입니다.");
        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Response> handleCartNotFoundException(CartNotFoundException e) {
        Response response = SimpleResponse.badRequest(e.getMessage());
        return ResponseEntity.badRequest()
                .body(response);
    }
}
