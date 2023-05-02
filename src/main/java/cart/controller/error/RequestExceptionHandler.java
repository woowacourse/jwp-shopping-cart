package cart.controller.error;

import cart.controller.ProductApiController;
import cart.controller.dto.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = {ProductApiController.class})
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        ErrorResponse messages = ErrorResponse.createErrorResponseByMessage(exception.getBindingResult());

        return handleExceptionInternal(exception, messages, headers, status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.createErrorResponseByMessage(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse messages = ErrorResponse.createErrorResponseByMessage("8자리 숫자까지만 가능합니다.");

        return handleExceptionInternal(exception, messages, headers, status, request);
    }
}
