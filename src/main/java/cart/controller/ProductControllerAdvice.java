package cart.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> sendErrorMessage(final MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult()
                .getAllErrors();

        String exceptionMessage = errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" / "));

        return ResponseEntity
                .badRequest()
                .body(exceptionMessage);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> sendEmptyResultDataMessage() {
        String exceptionMessage = "주어진 정보에 해당하는 데이터를 찾지 못했습니다.";
        return ResponseEntity
                .badRequest()
                .body(exceptionMessage);
    }
}
