package cart.product.controller;

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> sendExceptionMessage(RuntimeException exception) {
        exception.printStackTrace();
        String exceptionMessage = "서버 내부의 오류로 인해 작업을 처리하지 못했습니다.";
        return ResponseEntity
                .internalServerError()
                .body(exceptionMessage);
    }
}
