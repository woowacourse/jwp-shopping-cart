package cart.exception;

import static java.util.stream.Collectors.joining;

import cart.exception.custom.ApplicationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        ExceptionResponse response = new ExceptionResponse("서버가 응답할 수 없습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException exception) {
        ExceptionResponse response = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.status(exception.status()).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        String inputType = exception.getValue().getClass().toString();
        String requiredType = exception.getRequiredType().toString();
        String message = String.format("잘못된 타입을 입력하였습니다. 입력 타입 : %s, 요구 타입: %s", inputType, requiredType);
        ExceptionResponse response = new ExceptionResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining(System.lineSeparator()));
        ExceptionResponse response = new ExceptionResponse(exceptionMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
