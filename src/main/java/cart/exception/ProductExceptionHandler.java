package cart.exception;

import static java.util.stream.Collectors.joining;

import cart.exception.custom.ApplicationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//TODO: contentType 예외 추가
@RestControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllException(Exception exception) {
        ExceptionResponse response = new ExceptionResponse("서버가 응답할 수 없습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.status(exception.status()).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        String inputType = ex.getValue().getClass().toString();
        String requiredType = ex.getRequiredType().toString();
        String message = String.format("잘못된 타입을 입력하였습니다. 입력 타입 : %s, 요구 타입: %s", inputType, requiredType);
        ExceptionResponse response = new ExceptionResponse(message);
        return handleExceptionInternal(response, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String exceptionMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining(System.lineSeparator()));
        ExceptionResponse response = new ExceptionResponse(exceptionMessage);
        return handleExceptionInternal(response, status);
    }

    private ResponseEntity<Object> handleExceptionInternal(ExceptionResponse response, HttpStatus status) {
        return ResponseEntity.status(status).body(response);
    }
}
