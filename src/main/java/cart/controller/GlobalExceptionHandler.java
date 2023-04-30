package cart.controller;

import cart.dto.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(Exception exception) {
        return ResponseEntity.internalServerError().body(new ExceptionResponse("서버가 응답할 수 없습니다."));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final String exceptionMessage = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(System.lineSeparator()));
        
        return ResponseEntity.badRequest().body(new ExceptionResponse(exceptionMessage));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse("[ERROR] 가격의 최대 금액은 1000만원입니다."));
    }
}
