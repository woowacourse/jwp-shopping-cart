package woowacourse.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import woowacourse.shoppingcart.dto.ExceptionResponse;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(final BadRequestException e) {
        final ExceptionResponse response = new ExceptionResponse(List.of(e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }
}
